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

import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class WillGiveCharityUtils {
		
	public static List<Charity> getAllCharities() {
		Log.d("URL",ServerUrls.HOST_URL+ServerUrls.ALL_CHARITIES_PATH);
		List<Charity> charityList = new ArrayList<Charity>();
		
		HttpGet requestMeta = new HttpGet(ServerUrls.HOST_URL+ServerUrls.ALL_CHARITIES_PATH);
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
		        String videoUrl = (String) charityJson.get("videoUrl");
		        String status = (String) charityJson.get("status");

		        
		        Charity charity = new Charity( charityId,  email,  name,  ein,
		    			category, address, city, state,
		    			country, zipcode, phone, fax,
		    			mission, ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX, videoUrl, website,
		    			facebookUrl, 0.0, status,
		    			contactPersonName, contactPersonTitle);
				Log.d("Charity", "Id- "+ charityId+"; name- "+name+"; EIN- "+ein+"; Address- "+address);
				charityList.add(charity);
		        //Insert Course MetaData to DB
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return charityList;
	}

}
