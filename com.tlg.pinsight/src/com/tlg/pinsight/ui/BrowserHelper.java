package com.tlg.pinsight.ui;

import java.net.URL;

import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import com.sabre.edge.platform.core.ui.threading.UiThreadInvoker;
import com.sabre.edge.platform.core.ui.utils.LauncherParams;
import com.sabre.edge.platform.core.ui.utils.WorkbenchUtils;
import com.tlg.pinsight.Activator;
import com.tlg.pinsight.services.CoreServiceProvider;

public class BrowserHelper {

	public static void showInInternalBrowser(final String urlToLoad) {

		new UiThreadInvoker<Object>() {
			@Override
			protected Object invoke() {

				try {

					LauncherParams pmtsToOpenEditor = new LauncherParams.LauncherParamsBuilder(
							"com.tlg.pinsight.editor.CustomBrowserEditor",
							"pinSIGHT WebBrowser", Activator.PLUGIN_ID)
							.persist(true).url(urlToLoad).build();

					new WorkbenchUtils().openBrowserEditor(
							"com.tlg.pinsight.webkiteditor.command",
							pmtsToOpenEditor);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}.asyncExec();
	}

	public static void showInExternalBrowser(final String urlToLoad) {
		new UiThreadInvoker<Object>() {

			@Override
			protected Object invoke() {

				try {

					IWorkbenchBrowserSupport support = CoreServiceProvider
							.getBrowserSupportService();
					IWebBrowser browser = support.getExternalBrowser();
					browser.openURL(new URL(urlToLoad));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		}.asyncExec();
	}

}
