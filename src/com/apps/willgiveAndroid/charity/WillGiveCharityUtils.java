package com.apps.willgiveAndroid.charity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.apps.willgiveAndroid.utils.StringUtils;
import com.logentries.android.AndroidLogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;


public class WillGiveCharityUtils {
		
	public static CharityQRCode parseCharityQRCode(String qrContent, Context context){
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		//qrcontent : http://www.willgive.org/c/<EIN>?n=<name>&a=<Address>&p=<Phone>&m=<mission>
		CharityQRCode qrcode = null;
		
		//We don't have expire date currently, might be adding it later
		try {
			if( !qrContent.startsWith(Constants.WILLGIVE_QR_CODE_PREFIX) ) {
				return null;
			}
			String prefix = Constants.WILLGIVE_QR_CODE_PREFIX;
			String content =  qrContent.substring(prefix.length());
			Log.d("content", content);
			int markPosition = content.indexOf("?");
			// EIN=<EIN>, between "/" and "?"
			String EIN = content.substring(0, markPosition);
			Log.d("EIN", EIN);

			int recipientIdEnd = content.indexOf("&", markPosition+1);
			String recipientId = content.substring(markPosition+3, recipientIdEnd).replaceAll("^", " ");
			Log.d("recipientId", recipientId);
			//get name
			int nameEnd = content.indexOf("&", recipientIdEnd+1);
			String name = content.substring(recipientIdEnd+3, nameEnd).replaceAll("^", " ");
			Log.d("name", name);

			int addressEnd = content.indexOf("&", nameEnd+1);
			String address = content.substring(nameEnd+3, addressEnd).replaceAll("^", " ");;
			Log.d("address", address);

			int phoneEnd = content.indexOf("&", addressEnd+1);
			String phone = content.substring(addressEnd+3, phoneEnd).replaceAll("^", " ");;
			Log.d("phone", phone);

			String mission = content.substring(phoneEnd+3).replaceAll("^", " ");
			Log.d("mission", mission);

			qrcode = new CharityQRCode(prefix, recipientId, EIN, name, address, phone, mission, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return qrcode;
	}
	
	public static TransactionSummaryForCharity getTransactionSummaryForCharity(Long charityId, Long duration, Context context) {
		TransactionSummaryForCharity summary = null;
		
		return summary;
	}
	
	public static Charity getCharityByEIN(String EIN, Location location, Context context) {
		String url = ServerUrls.HOST_URL + ServerUrls.GET_CHARITY_BY_EIN_PATH + EIN;
		if(location != null) {
			url += "?latitude="+location.getLatitude()+"&longtitude="+location.getLongitude();
		}
		return getCharityHelper(url, context);
	}
	
	public static Charity getCharityById(String charityId, Context context) {
		Charity charity = null;
		String url = ServerUrls.HOST_URL + ServerUrls.GET_CHARITY_BY_ID_PATH + charityId;
		return getCharityHelper(url, context);
	}
	
	public static List<Charity> getAllCharities(long start, long count, Context context) {
		String url = ServerUrls.HOST_URL+ServerUrls.ALL_CHARITIES_PATH+"?start="+start+"&count="+count;		
		return getCharityListHelper(url, context);
	}
	
	public static List<Charity> searchCharityByKeyword(String keyword, Context context) {
		String url = ServerUrls.HOST_URL+ServerUrls.SEARCH_CHARITY_PATH+"?keyword="+keyword;
		return getCharityListHelper(url, context);
	}
	
	//get one charity
	private static Charity getCharityHelper(String url, Context context) {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		Charity charity = null;
		
		try {
			logger.info("Getting Charity from url - " + url);
			HttpGet requestMeta = new HttpGet(url);
			HttpResponse responseMeta = HttpClientFactory.getThreadSafeClient().execute(requestMeta);
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseMeta.getEntity().getContent(), "UTF-8"));
			String jsonStr = "";
			String str="";
			while((str=reader.readLine())!=null){
				jsonStr+=str;	
			}
			
			//handle failure SERVICE_CALL_FAILED
			//Log.d("DownloadUsingRestfulAPI", "getting meta data jsonStr "+ jsonStr);
			
		    JSONParser parser = new JSONParser();
			Object obj = parser.parse(jsonStr);
			JSONObject charityJson = (JSONObject) obj;	   
		    	
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
	        String facebookUrl = (String) charityJson.get("facebookUrl");
	        //Double rating = (Double) charityJson.get("rating"); //need to change DB, make it number
	        String imagePath = (String) charityJson.get("imagePath");
	        String videoUrl = (String) charityJson.get("videoUrl");
	        String status = (String) charityJson.get("status");
	        Boolean isFavored = StringUtils.getBooleanFromYOrN( (String) charityJson.get("isFavored") );
	        
	        charity = new Charity( charityId,  email,  name,  ein,
	    			category, address, city, state,
	    			country, zipcode, phone, fax,
	    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
	    			facebookUrl, imagePath, 0.0, status,
	    			contactPersonName, contactPersonTitle, isFavored );
			Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
	        //Insert Course MetaData to DB
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return charity;
	}

	//get a list of charities
	private static List<Charity> getCharityListHelper(String url, Context context) {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		List<Charity> results = new ArrayList<Charity>();
		
		try {
			HttpGet requestMeta = new HttpGet(url);
			logger.info("Get CharityList from url - " + url);
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
		        String facebookUrl = (String) charityJson.get("facebookUrl");
		        //Double rating = (Double) charityJson.get("rating"); //need to change DB, make it number
		        String imagePath = (String) charityJson.get("imagePath");
		        String videoUrl = (String) charityJson.get("videoUrl");
		        String status = (String) charityJson.get("status");
		        Boolean isFavored = StringUtils.getBooleanFromYOrN( (String) charityJson.get("isFavored") );

		        
		        Charity charity = new Charity( charityId,  email,  name,  ein,
		    			category, address, city, state,
		    			country, zipcode, phone, fax,
		    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
		    			facebookUrl, imagePath,  0.0, status,
		    			contactPersonName, contactPersonTitle, isFavored );
				Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
				results.add(charity);
		        //Insert Course MetaData to DB
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("TAG", e.getMessage());
			logger.error(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TAG", e.getMessage());
			logger.error(e.getMessage());
		}
		
		return results;
	}
}
