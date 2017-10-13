package com.sabre.tlg.redapp.extension.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;

public class AppPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public AppPreferencePage() {
		super(GRID);
		setPreferenceStore(CoreServicesHelper.getPreferenceStore());
		setDescription("Agent Flow Sample Red App, the app is configured to listen for :");
	}
	
	public void createFieldEditors() {
		
		
		
		
		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_LISTEN_AVAIL,
					"Listen for availability commands (1...)",
					SWT.WRAP,
					getFieldEditorParent()));

		
		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_LISTEN_SELL,
					"Listen for Sell commands (0...)",
					SWT.WRAP,
					getFieldEditorParent()));

		
		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_MODIFY_RCVDFROM,
					"Command MODIFICATION pattern, on Received From format (6...)",
					0,
					getFieldEditorParent()));
		

		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_BLOCK_ER,
					"Command BLOCK pattern, on End & Redisplay formats (E/ER).",
					SWT.WRAP,
					getFieldEditorParent()));


	}

	public void init(IWorkbench workbench) {
	}
	
}