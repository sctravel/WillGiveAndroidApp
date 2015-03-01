package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

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
		
		SharedPreferences userPref = activity.getSharedPreferences(Constants.USER_PREF_NAME, 0);
		Long userId = userPref.getLong("userId",0);

		//Service call to get user settings
		settings = WillGiveUserUtils.getUserSettings(userId);
		
		SharedPreferences pref = activity.getSharedPreferences(Constants.USER_SETTINGS_PREF_NAME, 0);
		SharedPreferences.Editor editor = pref.edit();
	    editor.putLong("maxAmountPerTime", settings.getMaxAmountPerTime());
	    editor.putLong("maxAmountDaily", settings.getMaxAmountDaily());
	    editor.putLong("defaultAmountPerTime", settings.getDefaultAmountPerTime());
	    editor.putBoolean("allowContributionPublic", settings.getAllowContributionPublic());
	    editor.putBoolean("receiveEmailNotification", settings.getReceiveEmailNotification());
	    editor.putBoolean("receiveEmailUpdate", settings.getReceiveEmailUpdate());
	    
	    // Commit the edits!
	    editor.commit();		
	   
	    return true;
	}
}
