package com.tlg.pinsight.managers;

import com.sabre.edge.cf.model.IEvent;
import com.tlg.pinsight.listeners.Constants;
import com.tlg.pinsight.ui.BrowserHelper;
import com.tlg.pinsight.ui.CfServicesHelper;

public class RedirectionManager {

	public void runInternalBrowserWorkFlow(IEvent evt, String cmdTyped,	String cmdResp) {
		if (cmdResp.equalsIgnoreCase(Constants.SIGN_IN_A)
				|| cmdResp.toLowerCase().contains("agent not signed in")
				|| cmdResp.toLowerCase().contains("invalid or expired binary security token")) {
			CfServicesHelper.showInEmulator(Constants.Need_Sign_In_Message);
		} else {
			//Internal Browser
			String siteUrl = redirectionUrl(cmdTyped, "Internal");
			BrowserHelper.showInInternalBrowser(siteUrl);
		}
		
	}
	
	public void runExternalBrowserWorkFlow(IEvent evt, String cmdTyped,
			String cmdResp) {
		if (cmdResp.equalsIgnoreCase(Constants.SIGN_IN_A)
				|| cmdResp.toLowerCase().contains("agent not signed in")
				|| cmdResp.toLowerCase().contains("invalid or expired binary security token")) {
			CfServicesHelper.showInEmulator(Constants.Need_Sign_In_Message);
		} else {
			// Open site in external browser
			// System.out.println("External Browser");

			String siteUrl = redirectionUrl(cmdTyped, "External");

			BrowserHelper.showInExternalBrowser(siteUrl);
		}
	}

	public String redirectionUrl(String cmdTyped, String browserType) {
		UrlManager urlManager = new UrlManager();
		String siteUrl = urlManager.getUrl(Constants.Redirect_booking_url);
		// System.out.println("siteUrl :"+ siteUrl);
		PnrManager pnrManager = new PnrManager();
		String pnr = pnrManager.getContextPNR();

		siteUrl = urlManager.buildRedirectionUrl(cmdTyped, siteUrl, pnr);

		if (pnr.equals("")) {
			CfServicesHelper.showInEmulator(String.format(Constants.Redirection_Message, browserType));
		} else {
			CfServicesHelper.showInEmulator(Constants.Redirection_Message_with_PNR + pnr);
		}
		return siteUrl;
	}
	
}
