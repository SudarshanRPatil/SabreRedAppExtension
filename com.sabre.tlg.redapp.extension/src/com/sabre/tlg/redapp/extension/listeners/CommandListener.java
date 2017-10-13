package com.sabre.tlg.redapp.extension.listeners;

import java.util.*;
import com.sabre.edge.cf.core.registry.service.ClientResponse;
import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.data.responses.EmulatorCommandResponse;
import com.sabre.edge.cf.model.IEvent;
import com.sabre.edge.cf.model.IEventListener;
import com.sabre.edge.cf.model.ServiceStatus;
import com.sabre.edge.cf.model.element.ServiceContext;
import com.sabre.edge.dynamo.cf.pnr.client.GetPnrServiceClient;
import com.sabre.edge.dynamo.cf.pnr.data.GetPnrRequest;
import com.sabre.edge.dynamo.cf.pnr.data.GetPnrResponse;
import com.sabre.edge.platform.core.config.base.ConfigKeyNotFoundException;
import com.sabre.edge.platform.core.config.base.IBaseConfigService;
import com.sabre.edge.platform.core.sso.base.IAgentProfileService;
import com.sabre.services.res.tir.v3_9.TravelItineraryReadRS;
import com.sabre.tlg.redapp.extension.Activator;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;
import com.sabre.tlg.redapp.extension.ui.BrowserHelper;
import com.sabre.tlg.redapp.extension.ui.CfServicesHelper;
import com.sabre.tlg.redapp.extension.ui.DataHelper;

public class CommandListener implements IEventListener {

	public void handleEvent(IEvent evt) {

            //Get context to read emulator command text.		
			ServiceContext sc = (ServiceContext)evt.getContext();
			EmulatorCommandRequest cmdRQ = (EmulatorCommandRequest) sc.getRequest();
			EmulatorCommandResponse cmdRS = (EmulatorCommandResponse) sc.getResponse();
			
			//Get the console command
			EmulatorCommand cmd = cmdRQ.getEmulatorCommand();
			String cmdTyped = cmd.getCommand();
			
			String cmdResp = cmdRS.getEmulatorResponse() != null ? cmdRS.getEmulatorResponse().getResponse(): "";
						
			sc.setResponse(cmdRS);
			
			sc.setStatus(ServiceStatus.SUCCESS);
			
			if(cmdResp.isEmpty() == false && cmdResp.equals("SIGN IN A"))
			{
				CfServicesHelper.showInEmulator("Please SignIn first");	
			}
			
			if(cmdTyped != null && cmdTyped.isEmpty() == false)
			{
				if(cmdTyped.contains("CXT")) //TODO: Need to fetch the commands from configuration
				{
					CfServicesHelper.showInEmulator("Loading pinsight configurations...");
					RunConfiguration();
				}
							
				//To check command post_fix
				if(cmdTyped.length() >= 3)
				{
					String lastChars = "";
					if(cmdTyped.length() > 3)
					{
						lastChars = cmdTyped.substring(cmdTyped.length() - 3);
						cmdTyped = cmdTyped.substring(0, cmdTyped.length() - 3);
					}
					else
					{
						lastChars = cmdTyped;
					}
	
					if(lastChars.contains("*PS")) // TODO: Need to fetch the commands from configuration
					{
						RunInternalBrowserWorkFlow(evt, cmdTyped, cmdResp);
					}
					else if (lastChars.contains("*PN")) // TODO: Need to fetch the commands from configuration
					{
						RunExternalBrowserWorkFlow(evt, cmdTyped, cmdResp);
					}
				}
			}
			else
			{
				CfServicesHelper.showInEmulator("Invalid or Empty Command");
			}
	}	
		
	public void RunInternalBrowserWorkFlow(IEvent evt, String cmdTyped, String cmdResp)
		{
			if(cmdResp.toLowerCase().equals("sign in a") 
					|| cmdResp.toLowerCase().contains("agent not signed in")
					|| cmdResp.toLowerCase().contains("invalid or expired binary security token"))
			{
				CfServicesHelper.showInEmulator("Please SignIn first");	
			}
			else 
			{	
				//System.out.println("External Browser");
				String siteUrl = DataHelper.GetConfiguredUrl("RedirectionUrl");
				//System.out.println("siteUrl :"+ siteUrl);
				String pnr = GetContextPNR();
										
				siteUrl = buildRedirectionUrl(cmdTyped, siteUrl, pnr);
				
				if(pnr.equals(""))
				{
					CfServicesHelper.showInEmulator("No PNR present in Context");				
				}
				else
				{
					CfServicesHelper.showInEmulator("Redirecting to pinSIGHT with existing PNR :"+ pnr);
				}
				
				CfServicesHelper.showInEmulator("Redirecting to pinSIGHT in internal browser");
				
				BrowserHelper.showBrowserEditor(siteUrl);
			}
		}

	public String buildRedirectionUrl(String cmdTyped, String siteUrl, String pnr) {
			
			IAgentProfileService agentProfileService =  CoreServicesHelper.getAgentProfile();
			String parameters = GetQueryStringParameter(cmdTyped , agentProfileService.getEmail(), pnr, agentProfileService.getAgentId(), agentProfileService.getPcc());	
			siteUrl = siteUrl.concat(parameters);
			return siteUrl;
	  }
 
	public void RunExternalBrowserWorkFlow(IEvent evt,String cmdTyped,String cmdResp)
	   {
		if(cmdResp.toLowerCase().equals("sign in a") 
				|| cmdResp.toLowerCase().contains("agent not signed in")
				|| cmdResp.toLowerCase().contains("invalid or expired binary security token"))
		{
			CfServicesHelper.showInEmulator("Please SignIn first");	
		}
			else
			{
				//Open site in external browser
				//System.out.println("External Browser");
				
				String siteUrl = GetConfiguredUrl();
				
				//System.out.println("siteUrl :"+ siteUrl);
				
				String pnr = GetContextPNR();
				
				if(pnr.equals(""))
					CfServicesHelper.showInEmulator("No PNR present in Context");
				else
					CfServicesHelper.showInEmulator("Redirecting to pinSIGHT with existing PNR :"+ pnr);
				
				siteUrl = buildRedirectionUrl(cmdTyped, siteUrl, pnr);
				
				CfServicesHelper.showInEmulator("Redirecting to pinSIGHT in external browser");
				
				BrowserHelper.showExternalBrowser(siteUrl);
			}
	   }
	
	public void RunConfiguration()
		{
			//System.out.println("Run Configuration");
			ArrayList<String> objs = DataHelper.Configurations();
			if(objs != null)
			{	
				Iterator<String> itr=objs.iterator();  
				  while(itr.hasNext()){ 
				   String a= itr.next();
				   a = a.substring(0, a.length() - 3).substring(1);  
				   //System.out.println("Run" + a );  
				   CfServicesHelper.showInEmulator(a);
				   break;
				  }  	
			}
		}
			
	public String GetConfiguredUrl()
		{
			IBaseConfigService serviceReference = CoreServicesHelper.getConfiguration();
			String siteUrl = "";
			try {
				siteUrl = serviceReference.getLocalProperty("RedirectionUrl", Activator.PLUGIN_ID);
				System.out.println("URL : " + siteUrl);			
			} catch (ConfigKeyNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception at the time to get configured url");
				e.printStackTrace();
			}
			return siteUrl;
		}
		
	public String GetContextPNR()	{
		  	String pnr = "";
			ISRWCommunication communication = CoreServicesHelper.getISRWCommunication();
			ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp = new GetPnrServiceClient<TravelItineraryReadRS>(communication).send(new GetPnrRequest("3.9.0")); //TODO: fetch request the version from configuration
				if (isValidResponse(rsp)) {
				 //System.out.println("PNR : " + rsp.getPayload().getTirResponse().getTravelItinerary().getItineraryRef().getID().toString());
				 pnr = getPNR(rsp);
				 }
			return pnr;
		}

	private String getPNR(ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp) {
			return rsp.getPayload().getTirResponse().getTravelItinerary().getItineraryRef().getID().toString();
		}

	private boolean isValidResponse(ClientResponse<GetPnrResponse<TravelItineraryReadRS>> rsp) {
			return rsp != null && rsp.isSuccess() && rsp.getPayload() != null 
					&& rsp.getPayload().getTirResponse() != null 
					&& rsp.getPayload().getTirResponse().getTravelItinerary() != null;
		}
	
	public String GetQueryStringParameter(String cmdTyped, String agentEmail, String pnr, String agentId, String agentPcc)
		{
			String queryStringToPass = "";
			try {
				queryStringToPass = ("?sourcetype=gdsoverlay")
							.concat("&command="+ cmdTyped)
							.concat("&agentId="+ agentId)
							.concat("&agentPcc="+ agentPcc)
							.concat("&agentEmail="+ agentEmail)
							.concat("&pnr="+ pnr)
							.concat("&supplier=sabre");
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return queryStringToPass;
		}
		
}
