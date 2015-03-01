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

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class WillGiveCharityUtils {
		
	public static CharityQRCode parseCharityQRCode(String qrContent){
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

			//get name
			int nameEnd = content.indexOf("&", markPosition+1);
			String name = content.substring(markPosition+3, nameEnd).replaceAll("^", " ");
			Log.d("name", name);

			int addressEnd = content.indexOf("&", nameEnd+1);
			String address = content.substring(nameEnd+3, addressEnd).replaceAll("^", " ");;
			Log.d("address", address);

			int phoneEnd = content.indexOf("&", addressEnd+1);
			String phone = content.substring(addressEnd+3, phoneEnd).replaceAll("^", " ");;
			Log.d("phone", phone);

			String mission = content.substring(phoneEnd+3).replaceAll("^", " ");
			Log.d("mission", mission);

			qrcode = new CharityQRCode(prefix, EIN, name, address, phone, mission, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qrcode;
	}
	
	
	public static Charity getCharityByEIN(String EIN) {
		String url = ServerUrls.HOST_URL + ServerUrls.GET_CHARITY_BY_EIN_PATH + EIN;
		return getCharityHelper(url);
	}
	
	public static Charity getCharityById(String charityId) {
		Charity charity = null;
		String url = ServerUrls.HOST_URL + ServerUrls.GET_CHARITY_BY_ID_PATH + charityId;
		return getCharityHelper(url);
	}
	
	public static List<Charity> getAllCharities(long start, long count) {
		String url = ServerUrls.HOST_URL+ServerUrls.ALL_CHARITIES_PATH+"?start="+start+"&count="+count;		
		return getCharityListHelper(url);
	}
	
	public static List<Charity> searchCharityByKeyword(String keyword) {
		String url = ServerUrls.HOST_URL+ServerUrls.SEARCH_CHARITY_PATH+"?keyword="+keyword;
		return getCharityListHelper(url);
	}
	
	//get one charity
	private static Charity getCharityHelper(String url) {
		
		Charity charity = null;
		Log.d("URL", url);
		HttpGet requestMeta = new HttpGet(url);
		try {
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
	        String facebookUrl = (String) charityJson.get("facebookUrl");
	        //Double rating = (Double) charityJson.get("rating"); //need to change DB, make it number
	        String imagePath = (String) charityJson.get("imagePath");
	        String videoUrl = (String) charityJson.get("videoUrl");
	        String status = (String) charityJson.get("status");

	        
	        charity = new Charity( charityId,  email,  name,  ein,
	    			category, address, city, state,
	    			country, zipcode, phone, fax,
	    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
	    			facebookUrl, imagePath, 0.0, status,
	    			contactPersonName, contactPersonTitle);
			Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
	        //Insert Course MetaData to DB
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return charity;
	}

	//get a list of charities
	private static List<Charity> getCharityListHelper(String url) {
		List<Charity> results = new ArrayList<Charity>();
		
		Log.d("URL", url);

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
		        String facebookUrl = (String) charityJson.get("facebookUrl");
		        //Double rating = (Double) charityJson.get("rating"); //need to change DB, make it number
		        String imagePath = (String) charityJson.get("imagePath");
		        String videoUrl = (String) charityJson.get("videoUrl");
		        String status = (String) charityJson.get("status");

		        
		        Charity charity = new Charity( charityId,  email,  name,  ein,
		    			category, address, city, state,
		    			country, zipcode, phone, fax,
		    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
		    			facebookUrl, imagePath,  0.0, status,
		    			contactPersonName, contactPersonTitle);
				Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
				results.add(charity);
		        //Insert Course MetaData to DB
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
}
