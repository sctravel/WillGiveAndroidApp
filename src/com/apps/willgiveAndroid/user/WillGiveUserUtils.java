package com.apps.willgiveAndroid.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.util.Log;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.apps.willgiveAndroid.utils.StringUtils;

public class WillGiveUserUtils {
	
	public static UserSettings getUserSettings( Long userId) {
		
		UserSettings userSettings = null;
		
		try {
		    // Add your data
		    HttpGet httpget = new HttpGet(ServerUrls.HOST_URL+ServerUrls.GET_USER_SETTINGS_PATH);

		    HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(httpget);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(responseMeta.getEntity().getContent(), "UTF-8"));
			String jsonStr = "";
			String str="";
			while((str=reader.readLine())!=null){
				jsonStr+=str;	
			}
			
			
		    JSONParser parser = new JSONParser();
			JSONObject settingsJSON = (JSONObject) parser.parse(jsonStr);
			
			Long maxAmountDaily = (Long) settingsJSON.get("maxAmountDaily");
			Long maxAmountPerTime = (Long) settingsJSON.get("maxAmountPerTime");
			Long defaultAmountPerTime = (Long) settingsJSON.get("defaultAmountPerTime");
			Boolean receiveEmailUpdate = StringUtils.getBooleanFromYOrN( (String) settingsJSON.get("receiveEmailUpdate") );
			Boolean receiveEmailNotification = StringUtils.getBooleanFromYOrN( (String) settingsJSON.get("receiveEmailNotification") );
			Boolean allowContributionPublic = StringUtils.getBooleanFromYOrN( (String) settingsJSON.get("allowContributionPublic") );
			
			userSettings = new UserSettings(userId, maxAmountDaily, maxAmountPerTime, defaultAmountPerTime, 
					receiveEmailUpdate, receiveEmailNotification, allowContributionPublic);
			
		    Log.d("getUserSettings", "Success");
		      
		    

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		    
		
		return userSettings;
	}
}
