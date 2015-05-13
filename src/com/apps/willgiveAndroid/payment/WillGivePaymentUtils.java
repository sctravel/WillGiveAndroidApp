package com.apps.willgiveAndroid.payment;

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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;
import com.logentries.android.AndroidLogger;

public class WillGivePaymentUtils {
	public static boolean sendPledge(Long userId, Double amount, Long recipientId, String notes, Context context) {
		AndroidLogger logger = AndroidLogger.getLogger(context, Constants.ANDROID_LOG_UUID, false);

		boolean isFinished = false;
		
		try {
			HttpPost httppost = new HttpPost(ServerUrls.HOST_URL+ServerUrls.PAYMENT_USER_PLEDGE_PATH);
	
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		    nameValuePairs.add(new BasicNameValuePair("userId", ""+userId));
		    nameValuePairs.add(new BasicNameValuePair("amount", ""+amount));
		    nameValuePairs.add(new BasicNameValuePair("recipientId", ""+recipientId));	
		    nameValuePairs.add(new BasicNameValuePair("notes", ""+notes));	   

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

		    if(result.trim().equalsIgnoreCase(Constants.SERVICE_CALL_SUCCESS)) {
		    	return true;
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

	   
		return isFinished;
	}
}
