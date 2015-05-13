package com.apps.willgiveAndroid.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.user.UserSettings;
import com.apps.willgiveAndroid.user.WillGiveUserUtils;

public class FacebookLoginAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private User user;
	private UserLoginFragment fragment;
	private String accessToken;
	private String refreshToken;
	
	public FacebookLoginAsyncTask(UserLoginFragment fragment, String accessToken, String refreshToken){		
		this.fragment = fragment;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
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
			fragment.loginFailedUIUpdate();
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		Log.w("FB Login Async", "runing FB login async task");
		//TODO:  use correct username password
		user = WillGiveLoginUtils.facebookLogin(accessToken, refreshToken, context);
		if(user != null)  {   
			//Store user object to shared preferences
			WillGiveUserUtils.saveUserObjectToPreferences(user, context);
			//Syncing the settings once user logged in 
			// no password
			WillGiveUserUtils.saveUserCredentialPreferences(user.getEmail(), "", Constants.WILLGIVE_LOGIN_PROVIDER_FACEBOOK, context);
			
			return true;	
		} 
		
		return false;
	}

}
