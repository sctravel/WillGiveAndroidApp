package com.apps.willgiveAndroid.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.apps.willgiveAndroid.utils.StringUtils;

public class WillGiveUserUtils {
	
	public static void saveUserCredentialPreferences(String username, String password, String provider, Context context) {
		SharedPreferences userCredentialPref = context.getSharedPreferences(Constants.USER_CREDENTIALS_PREF_NAME, 0);
		SharedPreferences.Editor editor = userCredentialPref.edit();
	    editor.putString("email", username);
	    editor.putString("password", password);
	    editor.putString("provider", provider);
	    
	    // Commit the edits!
	    editor.commit();	
	}
	
	public static void saveUserSettingsPreferences(UserSettings settings, Context context) {
		SharedPreferences pref = context.getSharedPreferences(Constants.USER_SETTINGS_PREF_NAME, 0);
		SharedPreferences.Editor editor = pref.edit();
	    editor.putLong("userId", settings.getUserId());
	    editor.putLong("maxAmountPerTime", settings.getMaxAmountPerTime());
	    editor.putLong("maxAmountDaily", settings.getMaxAmountDaily());
	    editor.putLong("defaultAmountPerTime", settings.getDefaultAmountPerTime());
	    editor.putBoolean("allowContributionPublic", settings.getAllowContributionPublic());
	    editor.putBoolean("receiveEmailNotification", settings.getReceiveEmailNotification());
	    editor.putBoolean("receiveEmailUpdate", settings.getReceiveEmailUpdate());
	    
	    // Commit the edits!
	    editor.commit();
	}
	
	public static UserSettings getUserSettingsFromPreferences(Context context) {
		SharedPreferences userSettingsPref = context.getSharedPreferences(Constants.USER_SETTINGS_PREF_NAME, 0);
		
		Long userId = userSettingsPref.getLong("userId", 0);
		Long maxAmountPerTime = userSettingsPref.getLong("maxAmountPerTime", 0);
		Long maxAmountDaily = userSettingsPref.getLong("maxAmountDaily", 0);
		Long defaultAmountPerTime = userSettingsPref.getLong("defaultAmountPerTime", 0);

		Boolean allowContributionPublic = userSettingsPref.getBoolean("allowContributionPublic", true);
		Boolean receiveEmailNotification = userSettingsPref.getBoolean("receiveEmailNotification", true);
		Boolean receiveEmailUpdate = userSettingsPref.getBoolean("receiveEmailUpdate", true);

		
		UserSettings settings = new UserSettings( userId, maxAmountDaily,
				maxAmountPerTime, defaultAmountPerTime,
			    receiveEmailUpdate, receiveEmailNotification,
				allowContributionPublic);
		return settings;
	}
	
	public static User getUserObjectFromPreferences(Context context) {
		
		SharedPreferences userPref = context.getSharedPreferences(Constants.USER_PREF_NAME, 0);
		Long userId = userPref.getLong("userId", 0);
		String firstName = userPref.getString("firstName", "");
		String lastName = userPref.getString("lastName", "");
		String provider = userPref.getString("provider", "");
		String email = userPref.getString("email", "");
		String imageIconUrl = userPref.getString("imageIconUrl", "");
		
		User user = new User(userId, email, firstName, lastName, imageIconUrl, provider);
		
		return user;
		
	}
	public static void saveUserObjectToPreferences(User user, Context context) {
		SharedPreferences userPref = context.getSharedPreferences(Constants.USER_PREF_NAME, 0);
		Editor userPrefEditor = userPref.edit();
		userPrefEditor.putLong("userId", user.getId());
		userPrefEditor.putString("email", user.getEmail());
		userPrefEditor.putString("firstName", user.getFirstName());
		userPrefEditor.putString("lastName", user.getLastName());
		userPrefEditor.putString("provider", user.getProvider());
		userPrefEditor.putString("imageIconUrl", user.getImageIconUrl());
		userPrefEditor.commit();
	}
	
	public static List<UserTransaction> getUserTransactionHistory(Long userId) {
		List<UserTransaction> transactionHistory = new ArrayList<UserTransaction>();
		String url = ServerUrls.HOST_URL+ServerUrls.GET_USER_TRANSACTIONS_PATH;
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
			JSONArray transactions = (JSONArray) obj;
			
		    Iterator<JSONObject> iterator = transactions.iterator();
		    while (iterator.hasNext()) {
		    	JSONObject transactionJson = (JSONObject)iterator.next();
		    	
		    	String transactionId = (String) transactionJson.get("transactionId");
		    	String amount = (String) transactionJson.get("amount").toString();
		    	Log.d("amount", amount.toString());

		    	Long recipientId = (Long) transactionJson.get("recipientId");
		    	String recipientName = (String) transactionJson.get("name");
		    	String confirmationCode = (String) transactionJson.get("confirmationCode");
		    	String dateTime = (String) transactionJson.get("dateTime");
		    	String settleTime = (String) transactionJson.get("settleTime");
		    	String status = (String) transactionJson.get("status");
		    	
		    	UserTransaction tran = new UserTransaction(transactionId, confirmationCode, userId, recipientId, recipientName, Double.parseDouble(amount.toString()), dateTime, settleTime, status);
		    	Log.d("UserTransaction", tran.toString());
		    	transactionHistory.add(tran);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		return transactionHistory;
	}
	
	//TODO finish it
	public static boolean setUserFavorateCharity(String charityId, String value) {
		//This is a get request using query parameters  maybe need to change it
		String url = ServerUrls.HOST_URL + ServerUrls.SET_USER_FAV_CHARITY_PATH + "?rid="+charityId+"&value="+value;
		try {
			HttpGet requestMeta = new HttpGet(url);
			HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(requestMeta);
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseMeta.getEntity().getContent(), "UTF-8"));
			String jsonStr = "";
			String str="";
			while((str=reader.readLine())!=null){
				jsonStr+=str;	
			}
			
			if(jsonStr.equalsIgnoreCase(Constants.SERVICE_CALL_SUCCESS)) {
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("WillGiveUserUtil", "caught unknow exception");
			e.printStackTrace();
		}
		return false;
	}
	
	//Need to login, userId is in the session
	public static List<Charity> getUserFavorateCharityList() {
		List<Charity> favCharityList = new ArrayList<Charity>();
		String url = ServerUrls.HOST_URL + ServerUrls.GET_USER_FAV_CHARITIES_PATH;
		try {
			HttpGet requestMeta = new HttpGet(url);
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
		    	
		        Long charityId = (Long) charityJson.get("recipientId");
		        String name = (String) charityJson.get("name");
		        String email = (String) charityJson.get("email");
		        String ein = (String) charityJson.get("ein");
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
		        Boolean isFavored = StringUtils.getBooleanFromYOrN( (String) charityJson.get("isFavored") );

		        
		        Charity charity = new Charity( charityId,  email,  name,  ein,
		    			category, address, city, state,
		    			country, zipcode, phone, fax,
		    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
		    			facebookUrl, imagePath, 0.0, status,
		    			contactPersonName, contactPersonTitle, isFavored );
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
		} catch (Exception e) {
			Log.e("WillGiveUserUtil", "caught unknow exception");
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
		    for( Header httpMessage : responseMeta.getAllHeaders() ) {
			    Log.v("httpMessage", httpMessage.getName()+": "+httpMessage.getValue());

		    }
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
