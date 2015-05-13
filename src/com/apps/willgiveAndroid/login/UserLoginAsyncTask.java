package com.apps.willgiveAndroid.login;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.user.RetrieveUserSettingsAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.user.UserSettings;
import com.apps.willgiveAndroid.user.WillGiveUserUtils;

import android.content.SharedPreferences;
import android.graphics.Color;

public class UserLoginAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private User user;
	private String username;
	private String password;
	private Fragment fragment;
	
	public UserLoginAsyncTask(Fragment fragment, String username, String password){		
		this.fragment = fragment;
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
					
		if(result) {		    	
		    Intent intent = new Intent(fragment.getActivity(), com.apps.willgiveAndroid.WillGiveMainPageActivity.class);
		    intent.putExtra("user", user);    
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    fragment.getActivity().startActivity(intent);
		    fragment.getActivity().finish();
		} else {
			if(fragment instanceof UserLoginFragment ) {
				((UserLoginFragment) fragment).loginFailedUIUpdate();
			}
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		//
		//Disable login button and display logging in ... message	
		user = WillGiveLoginUtils.postToLogin(username, password, context);
		if(user != null)  {
			//Store user object to shared preferences
			WillGiveUserUtils.saveUserObjectToPreferences(user, context);
			//Syncing the settings once user logged in 
			WillGiveUserUtils.saveUserCredentialPreferences(username, password, Constants.WILLGIVE_LOGIN_PROVIDER_WILLGIVE, context);		
			return true;
		}
		
		return false;
	}

}
