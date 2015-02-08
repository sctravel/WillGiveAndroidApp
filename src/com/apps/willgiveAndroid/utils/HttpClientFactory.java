package com.apps.willgiveAndroid.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

public class HttpClientFactory {

    private static DefaultHttpClient client;

    public synchronized static HttpClient getThreadSafeClient() {
  
        if (client != null)
            return client;
         
        client = new DefaultHttpClient();
       
        ClientConnectionManager mgr = client.getConnectionManager();
        
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(
        		new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), 
        		params
        		);
        
        client.getCookieSpecs().register("lenient", new CookieSpecFactory() {
            public CookieSpec newInstance(HttpParams params) {
                return new LenientCookieSpec();
            }
        });
        HttpClientParams.setCookiePolicy(client.getParams(), "lenient");

        return client;
    } 
}