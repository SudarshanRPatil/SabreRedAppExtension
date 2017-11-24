package com.tlg.pinsight.services;

import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.platform.core.config.base.IBaseConfigService;
import com.sabre.edge.platform.core.sso.base.IAgentProfileService;
import com.tlg.pinsight.Activator;

public class CoreServiceProvider {
	
	static Activator activator = Activator.getDefault();
		
	public static IAgentProfileService getAgentProfileService(){
		return activator.getProfileService();
	}
	
	public static IBaseConfigService getConfigurationService(){
		return activator.getServiceReference(IBaseConfigService.class);
	}
	
	public static ISRWCommunication getISRWCommunicationService(){
		return activator.getServiceReference(ISRWCommunication.class);
	}
	
	public static IWorkbenchBrowserSupport getBrowserSupportService(){
		return activator.getWorkbench().getBrowserSupport();
	}
		
}


