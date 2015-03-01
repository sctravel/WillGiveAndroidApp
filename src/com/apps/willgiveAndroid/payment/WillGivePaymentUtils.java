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

import android.util.Log;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.utils.HttpClientFactory;

public class WillGivePaymentUtils {
	public static boolean sendPledge(Long userId, Double amount, Long recipientId) {
		boolean isFinished = false;
		
		try {
			HttpPost httppost = new HttpPost(ServerUrls.HOST_URL+ServerUrls.PAYMENT_USER_PLEDGE_PATH);
	
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		    nameValuePairs.add(new BasicNameValuePair("userId", ""+userId));
		    nameValuePairs.add(new BasicNameValuePair("amount", ""+amount));
		    nameValuePairs.add(new BasicNameValuePair("recipientId", ""+recipientId));	   
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	   
		return isFinished;
	}
}
