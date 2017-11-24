package com.tlg.pinsight.managers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sabre.edge.cf.core.registry.service.ClientResponse;
import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.model.IEvent;
import com.sabre.edge.dynamo.cf.pnr.client.GetPnrServiceClient;
import com.sabre.edge.dynamo.cf.pnr.data.GetPnrRequest;
import com.sabre.edge.dynamo.cf.pnr.data.GetPnrResponse;
import com.sabre.edge.platform.core.sso.base.IAgentProfileService;
import com.sabre.services.res.tir.v3_9.TravelItineraryReadRS;
import com.tlg.pinsight.listeners.Constants;
import com.tlg.pinsight.services.CoreServiceProvider;
import com.tlg.pinsight.ui.CfServicesHelper;

public class PnrManager {

	public void savePNRDetails(IEvent evt, String cmdTyped, String cmdResp) {

		UrlManager urlManager = new UrlManager();
		String apiUrl = urlManager.getUrl(Constants.Save_booking_url);

		//TODO: need to remove below line after testing
		cmdResp = "RECORD LOCATOR REQUESTED 1.1TEST/SANDBOX 1 3K 555H 25AUG F ;INSGN HK1 710A 815A /ABRQ /E 2 VN 603N 25AUG F SGNBKK HK1 455P 630P /DCVN*WVXQKI /E TKT/TIME LIMIT 1.TAW/ PHONES 1.GSP9096359750-H-1.1 EMAIL ADDRESS 1.Â?SPATIL@TAVISCA.COMÂ? 1.1 TEST/SANDBOX CUSTOMER NUMBER - 003285301P PRICE QUOTE RECORD EXISTS SECURITY INFO EXISTS *P3D OR *P4D TO DISPLAY RECEIVED FROM - SANDBOXTEST F1HF.F1HF*AW1 0115/31JUL17 WVXQBH H";

		IAgentProfileService agentProfileService = CoreServiceProvider.getAgentProfileService();
		TrackingManager trackingManager = new TrackingManager();

		String pnr = getPNRFromResponse(cmdResp);
		String agentId = agentProfileService.getAgentId();
		String agentPcc = agentProfileService.getPcc();
		String agentEmail = agentProfileService.getEmail();
		
		boolean isSaveBookingsuccesful = trackingManager.trackBooking(apiUrl, agentId, agentPcc, agentEmail, pnr);

		if (isSaveBookingsuccesful) {
			CfServicesHelper.showInEmulator(Constants.Pnr_saved_Message);
		}
		CfServicesHelper
				.showInEmulator(Constants.Pnr_saved_failure_Message);
	}

	public String getPNRFromResponse(String cmdResp) {
		String item = "";

		String regexe = "(?<=(?<=/){1}([\\w]{7}) )([\\w]{6})";

		Pattern pattern = Pattern.compile(regexe);

		Matcher matcher = pattern.matcher(cmdResp);

		ArrayList<String> result = new ArrayList<String>();

		if (matcher.find()) {
			result.add(matcher.group());
			while (matcher.find()) {
				result.add(matcher.group());
			}
		}

		if (result != null && !result.isEmpty()) {
			item = result.get(result.size() - 1);
			return item;
		}

		return item;
	}

	public String getContextPNR() {
		String pnr = "";
		ISRWCommunication communication = CoreServiceProvider.getISRWCommunicationService();
		ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp = new GetPnrServiceClient<TravelItineraryReadRS>(communication).send(new GetPnrRequest("3.9.0"));
		// TODO: fetch request the version from configuration
		if (isValidResponse(rsp)) {
			// System.out.println("PNR : " +
			// rsp.getPayload().getTirResponse().getTravelItinerary().getItineraryRef().getID().toString());
			pnr = getPNR(rsp);
		}
		return pnr;
	}
	
	private String getPNR(
			ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp) {
		return rsp.getPayload().getTirResponse().getTravelItinerary()
				.getItineraryRef().getID().toString();
	}

	private boolean isValidResponse(
			ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp) {
		return rsp != null
				&& rsp.isSuccess()
				&& rsp.getPayload() != null
				&& rsp.getPayload().getTirResponse() != null
				&& rsp.getPayload().getTirResponse().getTravelItinerary() != null;
	}
}
