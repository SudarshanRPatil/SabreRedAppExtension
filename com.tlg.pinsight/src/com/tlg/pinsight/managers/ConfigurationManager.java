package com.tlg.pinsight.managers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import com.tlg.pinsight.ui.CfServicesHelper;


public class ConfigurationManager {
	
	public void getConfiguration(String url) {
		
		UrlManager urlManager = new UrlManager();
		String httpsURL = urlManager.getUrl(url);
		ArrayList<String> objs = configurations(httpsURL);
		if (objs != null) {
			Iterator<String> itr = objs.iterator();
			while (itr.hasNext()) {
				String a = itr.next();
				a = a.substring(0, a.length() - 3).substring(1);
				CfServicesHelper.showInEmulator(a);
				break;
			}
		}
	}
	
	public static ArrayList<String> configurations(String httpsURL) {
		try {
			URL myurl = new URL(httpsURL);
			HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
			InputStream ins = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(ins);
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			ArrayList<String> obj = new ArrayList<String>();
			while ((inputLine = in.readLine()) != null) {
				String[] result = inputLine.split(",");

				for (int index = 0; index < result.length; index++) {
					if (index % 2 == 0) {
						// do nothing
					} else {
						String[] result1 = result[index].split(":");
						obj.add(result1[1]);
					}
				}
			}
			in.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
