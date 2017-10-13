package com.sabre.tlg.redapp.extension.ui;
//package org.gopaldas.readsps;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.sabre.edge.platform.core.config.base.ConfigKeyNotFoundException;
import com.sabre.edge.platform.core.config.base.IBaseConfigService;
import com.sabre.tlg.redapp.extension.Activator;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;

public class DataHelper {
	
	public static ArrayList<String> Configurations() 
     {
    	 try{
    		    String httpsURL = GetConfiguredUrl("ConfigurationUrl");		    
    		    URL myurl = new URL(httpsURL);
    		    HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
    		    InputStream ins = con.getInputStream();
    		    InputStreamReader isr = new InputStreamReader(ins);
    		    BufferedReader in = new BufferedReader(isr);
    		    String inputLine;
    		    ArrayList<String> obj = new ArrayList<String>();
    		    while ((inputLine = in.readLine()) != null)
    		    {        		        
    		    	String[] result = inputLine.split(",");
    		    	
    		    	for(int index =0; index < result.length; index++ )
    		    	{				
    		    		if(index % 2 == 0)
    		    		{
    		    			//do nothing
    		    		}
    		    		else
    		    		{
    		    		String[] result1 = result[index].split(":");
    		    		
    		    		obj.add(result1[1]);
    		    		
    		    		}
    		    	}
    		    
    		    }

    		    in.close();
    		    
    		    return obj;
    		 
            }
       catch(Exception e)
       {
    	   e.printStackTrace();
       }
		return null;
     }

	public static String GetConfiguredUrl(String key)
	{
		IBaseConfigService serviceReference = CoreServicesHelper.getConfiguration();
		String siteUrl = "";
		try {
			siteUrl = serviceReference.getLocalProperty(key, Activator.PLUGIN_ID);
			System.out.println("URL : " + siteUrl);
		} catch (ConfigKeyNotFoundException e) {
			e.printStackTrace();
		}
		return siteUrl;
	}

}


