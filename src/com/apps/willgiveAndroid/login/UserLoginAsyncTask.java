package com.apps.willgiveAndroid.login;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.user.UserSettings;

import android.content.SharedPreferences;
import android.graphics.Color;

public class UserLoginAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private User user;
	private WillGiveLoginActivity activity;
	
	public UserLoginAsyncTask(WillGiveLoginActivity activity){		
		this.activity = activity;
		Log.d("@@@@@", activity.getPackageName());
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
					
		if(result) {		    	
		    Intent intent = new Intent(activity, com.apps.willgiveAndroid.WillGiveMainPageActivity.class);
		    intent.putExtra("user", user);     
		    activity.startActivity(intent);
		    //activity.finish();
		} else {
			activity.getMessageView().setText("Email or password is incorrect.");
			activity.getMessageView().setTextColor(Color.RED);
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		user = WillGiveLoginUtils.postToLogin("tuxi1987@gmail.com", "11111111");
		if(user != null)     
			return true;
		
		return false;
	}

}
