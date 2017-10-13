package com.sabre.tlg.redapp.extension.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = CoreServicesHelper.getPreferenceStore();
		
		store.setDefault(PreferenceConstants.P_LISTEN_AVAIL, true);
		store.setDefault(PreferenceConstants.P_LISTEN_SELL, true);
		store.setDefault(PreferenceConstants.P_MODIFY_RCVDFROM, true);
		store.setDefault(PreferenceConstants.P_BLOCK_ER, true);
	}

}
