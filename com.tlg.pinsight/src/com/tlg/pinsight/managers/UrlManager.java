package com.tlg.pinsight.managers;

import com.sabre.edge.platform.core.config.base.ConfigKeyNotFoundException;
import com.sabre.edge.platform.core.config.base.IBaseConfigService;
import com.sabre.edge.platform.core.sso.base.IAgentProfileService;
import com.tlg.pinsight.Activator;
import com.tlg.pinsight.listeners.Constants;
import com.tlg.pinsight.services.CoreServiceProvider;

public class UrlManager {
	
	public String getUrl(String url) {
		IBaseConfigService serviceReference = CoreServiceProvider.getConfigurationService();
		String apiUrl = "";
		try {
			apiUrl = serviceReference.getLocalProperty(url, Activator.PLUGIN_ID);
			// System.out.println("URL : " + apiUrl);
		} catch (ConfigKeyNotFoundException e) {
			// TODO Auto-generated catch block
			// System.out.println("Exception at the time to get configured url");
			e.printStackTrace();
		}
		return apiUrl;
	}
	
	public String buildRedirectionUrl(String cmdTyped, String siteUrl,
			String pnr) {

		IAgentProfileService agentProfileService = CoreServiceProvider
				.getAgentProfileService();
		String parameters = getQueryStringParameter(cmdTyped,
				agentProfileService.getEmail(), pnr,
				agentProfileService.getAgentId(), agentProfileService.getPcc());
		siteUrl = siteUrl.concat(parameters);
		return siteUrl;
	}
	
	public String getQueryStringParameter(String cmdTyped, String agentEmail,
			String pnr, String agentId, String agentPcc) {
		String queryStringToPass = "";
		try {
	
				queryStringToPass = String.format("?sourcetype=%s&command=%s&agentId=%s&agentPcc=%s&agentEmail=%s&pnr=%s&supplier=%s", 
						Constants.SourceType,cmdTyped,agentId,agentPcc,agentEmail,pnr,Constants.Supplier);


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return queryStringToPass;
	}
}
