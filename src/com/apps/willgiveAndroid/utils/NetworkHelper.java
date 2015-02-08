package com.apps.willgiveAndroid.utils;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.util.Log;

//Not used for now, will try to use it, put all abstract network operation here
public class NetworkHelper {
	
	public static InputStream getInputStreamFromUrl(String url) {
	    InputStream content = null;
	    try {
	        HttpResponse response = HttpClientFactory.getThreadSafeClient().execute(new HttpGet(url));
	        content = response.getEntity().getContent();
	    } catch (Exception e) {
	        Log.d("Demo", "Network exception", e);
	    }
	    return content;
	}
}
