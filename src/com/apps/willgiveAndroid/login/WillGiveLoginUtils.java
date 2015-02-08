package com.apps.willgiveAndroid.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.HttpClientFactory;

public class WillGiveLoginUtils {

	public static Boolean logout() {
		try {
		    // Add your data
		    HttpGet httpget = new HttpGet(ServerUrls.HOST_URL+ServerUrls.LOG_OUT_PATH);
		    HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(httpget);
		    Log.d("Logout", "Success");
		    return true;	    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		    
		return false;
	}
	
	public static User postToLogin(String username, String password)  {
		User user = null;
		try {
		    // Add your data
		    HttpPost httppost = new HttpPost(ServerUrls.HOST_URL+ServerUrls.MOBILE_LOGIN_PATH);

		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", username));
		    nameValuePairs.add(new BasicNameValuePair("password", password));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    // Execute HTTP Post Request
		    HttpResponse httpResponse = HttpClientFactory.getThreadSafeClient().execute(httppost);
		    Log.v("Response", httpResponse.toString());
		    InputStream inputStream = httpResponse.getEntity().getContent();

		    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		    StringBuilder stringBuilder = new StringBuilder();

		    String bufferedStrChunk = null;

		    while((bufferedStrChunk = bufferedReader.readLine()) != null){
		        stringBuilder.append(bufferedStrChunk);
		    }
		    
		    String result = stringBuilder.toString();
		    Log.d("responseString", result);
		    
		    if(result.equalsIgnoreCase("failed")){
		    	return null;
		    } else {
		    	//UserPreferences.
		    	//TODO hardcode username and password here first
		    	return new User(1000000001L, "tuxi1987@gmail.com", "Xi", "TU", "", "willgive");
		    }
		    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
