package com.apps.willgiveAndroid.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.content.Context;
import android.util.Log;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.logentries.android.AndroidLogger;

public class WillGiveLoginUtils {

	public static boolean logout(Context context) {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		try {
		    // Add your data
		    HttpGet httpget = new HttpGet(ServerUrls.HOST_URL+ServerUrls.LOG_OUT_PATH);
		    HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(httpget);
		    logger.info("User logout Successfully");
		    return true;	    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}		    
		return false;
	}
	
	public static User facebookLogin(String accessToken, String refreshToken, Context context) {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		User user = null;
		try {
		    // Add your data
		    HttpPost httppost = new HttpPost(ServerUrls.HOST_URL+ServerUrls.MOBILE_FACEBOOK_LOGIN_PATH);

		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("access_token", accessToken));
		    nameValuePairs.add(new BasicNameValuePair("refresh_token", refreshToken));
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
		    
		    if(result.equalsIgnoreCase(Constants.SERVICE_CALL_FAILED )){
		    	return null;
		    } else {
		    	//UserPreferences.
		    	//TODO hardcode username and password here first
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(result);
				JSONObject userJson = (JSONObject) obj;
				
				String provider = (String) userJson.get("provider");
				String lastName = (String) userJson.get("lastName");
				String firstName = (String) userJson.get("firstName");
				Long userId = (Long) userJson.get("userId");
				String imageIconUrl = (String) userJson.get("imageIconUrl");
				String email = (String) userJson.get("email");
				
		    	return new User(userId, email, firstName, lastName, imageIconUrl, provider);
		    }
		    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return user;		
	}
	
	public static User postToSignup(String email, String password, String firstName, String lastName, Context context)  {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		User user = null;
		try {
		    // Add your data
		    HttpPost httppost = new HttpPost(ServerUrls.HOST_URL+ServerUrls.MOBILE_SIGNUP_PATH);

		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
		    nameValuePairs.add(new BasicNameValuePair("email", email));
		    nameValuePairs.add(new BasicNameValuePair("password", password));
		    nameValuePairs.add(new BasicNameValuePair("firstName", firstName));
		    nameValuePairs.add(new BasicNameValuePair("lastName", lastName));

		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    // Execute HTTP Post Request
		    HttpResponse httpResponse = HttpClientFactory.getThreadSafeClient().execute(httppost);
		    for( Header httpMessage : httpResponse.getAllHeaders() ) {
			    Log.v("httpMessage", httpMessage.getName()+": "+httpMessage.getValue());

		    }
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
		    
		    if(result.equalsIgnoreCase("failed") || result.equalsIgnoreCase("Unauthorized")){
		    	return null;
		    } else {
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(result);
				JSONObject userJson = (JSONObject) obj;
				
				String provider = (String) userJson.get("provider");
				//lastName = (String) userJson.get("lastName");
				//String firstName = (String) userJson.get("firstName");
				Long userId = (Long) userJson.get("userId");
				String imageIconUrl = (String) userJson.get("imageIconUrl");
				//String email = (String) userJson.get("email");
				
		    	return new User(userId, email, firstName, lastName, imageIconUrl, provider);
		    }
		    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return user;
	}
	
	public static User postToLogin(String username, String password, Context context)  {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

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
		    for( Header httpMessage : httpResponse.getAllHeaders() ) {
			    Log.v("httpMessage", httpMessage.getName()+": "+httpMessage.getValue());

		    }
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
		    
		    if(result.equalsIgnoreCase("failed") || result.equalsIgnoreCase("Unauthorized")){
		    	return null;
		    } else {
		    	JSONParser parser = new JSONParser();
				Object obj = parser.parse(result);
				JSONObject userJson = (JSONObject) obj;
				
				String provider = (String) userJson.get("provider");
				String lastName = (String) userJson.get("lastName");
				String firstName = (String) userJson.get("firstName");
				Long userId = (Long) userJson.get("userId");
				String imageIconUrl = (String) userJson.get("imageIconUrl");
				String email = (String) userJson.get("email");
				
		    	return new User(userId, email, firstName, lastName, imageIconUrl, provider);
		    }
		    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return user;
	}
}
