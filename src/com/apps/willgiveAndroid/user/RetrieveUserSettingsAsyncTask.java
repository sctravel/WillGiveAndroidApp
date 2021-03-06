package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;

public class RetrieveUserSettingsAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	private UserSettings settings;
	private WillGiveMainPageActivity activity;
	
	public RetrieveUserSettingsAsyncTask(WillGiveMainPageActivity activity){		
		this.activity =activity; 
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);				
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		Long userId = activity.getUser().getId();

		//Service call to get user settings
		settings = WillGiveUserUtils.getUserSettings(userId);
		if(settings != null ) {
			WillGiveUserUtils.saveUserSettingsPreferences(settings, context);
		    return true;
		} else {
			Log.e("RetrieveUserSettingsAsyncTask", "The user settings retrieved is null");
		}
		
		
	   
	    return false;
	}
}
