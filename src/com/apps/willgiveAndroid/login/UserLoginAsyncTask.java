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

import android.content.SharedPreferences;
import android.graphics.Color;

public class UserLoginAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private User user;
	private String username;
	private String password;
	private UserLoginFragment fragment;
	
	public UserLoginAsyncTask(UserLoginFragment fragment, String username, String password){		
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
			fragment.getMessageView().setText("Email or password is incorrect.");
			fragment.getMessageView().setTextColor(Color.RED);
			//fragment.getLoginButton().setEnabled(true);
			//fragment.getFBLoginButton().setEnabled(true);
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		//Disable login button and display logging in ... message
		//fragment.getLoginButton().setEnabled(false);
		//fragment.getFBLoginButton().setEnabled(false);
		fragment.getMessageView().setText("Logging in ...");

		
		user = WillGiveLoginUtils.postToLogin(username, password);
		if(user != null)  {
			//Syncing the settings once user logged in 
			SharedPreferences userCredentialPref = fragment.getActivity().getSharedPreferences(Constants.USER_CREDENTIALS_PREF_NAME, 0);
			SharedPreferences.Editor editor = userCredentialPref.edit();
		    editor.putString("email", username);
		    editor.putString("password", password);
		    editor.putString("provider", Constants.WILLGIVE_LOGIN_PROVIDER_WILLGIVE);
		    
		    // Commit the edits!
		    editor.commit();		
			return true;
		}
		
		return false;
	}

}
