package com.apps.willgiveAndroid.utils;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

//Not used for now, will try to use it, put all abstract network operation here
public class NetworkHelper {
	
	private static boolean hasNetworkConnection(Context context) {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	
	public static InputStream getInputStreamFromUrl(String url) {
	    InputStream content = null;
	    try {
	        HttpResponse response = HttpClientFactory.getThreadSafeClient().execute(new HttpGet(url));
	        content = response.getEntity().getContent();
	    } catch (Exception e) {
	        Log.d("NetworkHelper", "Network exception", e);
	    }
	    return content;
	}
}
