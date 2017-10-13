package com.sabre.tlg.redapp.extension.services;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.platform.core.config.base.IBaseConfigService;
import com.sabre.edge.platform.core.sso.base.IAgentProfileService;
import com.sabre.tlg.redapp.extension.Activator;

public class CoreServicesHelper {
	
	static Activator activator = Activator.getDefault();
		
	public static IAgentProfileService getAgentProfile(){
		return activator.getProfileService();
	}
	
	public static IBaseConfigService getConfiguration(){
		return activator.getServiceReference(IBaseConfigService.class);
	}
	
	public static ISRWCommunication getISRWCommunication(){
		return activator.getServiceReference(ISRWCommunication.class);
	}
	
	public static IWorkbenchBrowserSupport getBrowserSupport(){
		return activator.getWorkbench().getBrowserSupport();
	}
	
	public static IPreferenceStore getPreferenceStore(){
		return activator.getPreferenceStore();
	}
	
}


