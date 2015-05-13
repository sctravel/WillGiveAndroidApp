package com.apps.willgiveAndroid.login;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.user.RetrieveUserSettingsAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.user.UserSettings;
import com.apps.willgiveAndroid.user.WillGiveUserUtils;
import com.facebook.Session;

import android.content.SharedPreferences;
import android.graphics.Color;

public class UserLogoutAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private WillGiveMainPageActivity activity;
	
	public UserLogoutAsyncTask(WillGiveMainPageActivity activity){		
		this.activity = activity;
	}
	
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		Context context = contexts[0];
		boolean isLoggedOut = WillGiveLoginUtils.logout(context);
		if(isLoggedOut) {
			// start the login activity, delete the user preference
			
			Intent intent = new Intent( activity, WillGiveLoginActivity.class);
			intent.putExtra("isLoggedOut", true);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    activity.startActivity(intent);
		    activity.finish();
		}
		return isLoggedOut;
	}

}
