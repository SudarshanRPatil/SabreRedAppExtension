package com.sabre.tlg.redapp.extension.ui;

import java.net.URL;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import com.sabre.edge.platform.core.ui.threading.UiThreadInvoker;
import com.sabre.edge.platform.core.ui.utils.LauncherParams;
import com.sabre.edge.platform.core.ui.utils.WorkbenchUtils;
import com.sabre.tlg.redapp.extension.Activator;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;

public class BrowserHelper {

	public static void showAdvWebView(final String urlToLoad){
		
		new UiThreadInvoker<Object>() {
			@Override
			protected Object invoke() {
				
				LauncherParams pmtsToOpenView = new LauncherParams.LauncherParamsBuilder(
						"com.sabre.tlg.redapp.extension.view.WebKitSampleView", 
						"TLG WebBrowser View", 
						Activator.PLUGIN_ID)
					.persist(false)
					.url(urlToLoad)
					.build();
			
				new WorkbenchUtils().openBrowserView("com.sabre.tlg.redapp.extension.redapp.webkitview.command", pmtsToOpenView);
				return null;
			};
		}.asyncExec();
		
	}
	
	
	
	public static void showBrowserEditor(final String urlToLoad){

		new UiThreadInvoker<Object>() {
				@Override
				protected Object invoke() {
										
				try{	
					
					LauncherParams pmtsToOpenEditor = new LauncherParams.LauncherParamsBuilder(
							"com.sabre.tlg.redapp.extension.editor.CustomBrowserEditor", 
							"TLG WebBrowser Editor", 
							Activator.PLUGIN_ID)
						.persist(true)
						.url(urlToLoad)
						.build();
						
						new WorkbenchUtils().openBrowserEditor("com.sabre.tlg.redapp.extension.webkiteditor.command", pmtsToOpenEditor);
					 	//System.out.println("Updated browser url : " + urlToLoad);
					}
				catch(Exception e)
					{
						e.printStackTrace();
					}
				return null;
			}
		}.asyncExec();	
	}
	
	public static void showExternalBrowser(final String urlToLoad)
	{
		new UiThreadInvoker<Object>() {

			@Override
			protected Object invoke() {
	
				try {

					IWorkbenchBrowserSupport support =  CoreServicesHelper.getBrowserSupport();
					IWebBrowser browser = support.getExternalBrowser();
					browser.openURL(new URL(urlToLoad));
					
				    //Desktop.getDesktop().browse(new URL(urlToLoad).toURI());
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
				return null;	
			}
			
		}.asyncExec();
	}
		
}
