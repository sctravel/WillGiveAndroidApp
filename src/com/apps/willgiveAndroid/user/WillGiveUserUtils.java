package com.apps.willgiveAndroid.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.Log;

import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.apps.willgiveAndroid.utils.StringUtils;

public class WillGiveUserUtils {
	
	//TODO finish it
	public static boolean setUserFavorateCharity(String charityId, String value) {
		//This is a get request using query parameters  maybe need to change it
		String url = ServerUrls.HOST_URL + ServerUrls.SET_USER_FAV_CHARITY_PATH + "?rid="+charityId+"&value="+value;

		
		return true;
	}
	
	//Need to login, userId is in the session
	public static List<Charity> getUserFavorateCharityList() {
		List<Charity> favCharityList = new ArrayList<Charity>();
		String url = ServerUrls.HOST_URL + ServerUrls.GET_USER_FAV_CHARITIES_PATH;
		HttpGet requestMeta = new HttpGet(url);
		try {
			HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(requestMeta);
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseMeta.getEntity().getContent(), "UTF-8"));
			String jsonStr = "";
			String str="";
			while((str=reader.readLine())!=null){
				jsonStr+=str;	
			}
			
			//Log.d("DownloadUsingRestfulAPI", "getting meta data jsonStr "+ jsonStr);
			
		    JSONParser parser = new JSONParser();
			Object obj = parser.parse(jsonStr);
			JSONArray charities = (JSONArray) obj;
			
		    Iterator<JSONObject> iterator = charities.iterator();
		    while (iterator.hasNext()) {
		     	
		    	JSONObject charityJson = (JSONObject)iterator.next();
		    	
		        Long charityId = (Long) charityJson.get("recipient_id");
		        String name = (String) charityJson.get("name");
		        String email = (String) charityJson.get("email");
		        String ein = (String) charityJson.get("EIN");
		        String category = (String) charityJson.get("category");
		        String address = (String) charityJson.get("address");
		        String city = (String) charityJson.get("city");
		        String state = (String) charityJson.get("state");
		        String country = (String) charityJson.get("country");
		        String zipcode = (String) charityJson.get("zipcode");
		        String phone = (String) charityJson.get("phone");
		        String fax = (String) charityJson.get("fax");
		        String contactPersonName = (String) charityJson.get("contactPersonName");
		        String contactPersonTitle = (String) charityJson.get("contactPersonTitle");
		        String mission = (String) charityJson.get("mission");
		        String website = (String) charityJson.get("website");
		        String imagePath = (String) charityJson.get("imagePath");
		        String facebookUrl = (String) charityJson.get("facebookUrl");
		        //Double rating = (Double) charityJson.get("rating"); //need to change DB, make it number
		        String videoUrl = (String) charityJson.get("videoUrl");
		        String status = (String) charityJson.get("status");

		        
		        Charity charity = new Charity( charityId,  email,  name,  ein,
		    			category, address, city, state,
		    			country, zipcode, phone, fax,
		    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
		    			facebookUrl, imagePath, 0.0, status,
		    			contactPersonName, contactPersonTitle);
				Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
				favCharityList.add(charity);
		        //Insert Course MetaData to DB
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return favCharityList;
	}
	
	public static UserSettings getUserSettings( Long userId) {
		
		UserSettings userSettings = null;
		String url = ServerUrls.HOST_URL+ServerUrls.GET_USER_SETTINGS_PATH;
		try {
		    // Add your data
		    HttpGet httpget = new HttpGet(url);

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
