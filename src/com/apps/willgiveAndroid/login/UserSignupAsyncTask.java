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

public class UserSignupAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private User user;
	private String email;
	private String password;
	private String firstName;
	private String lastName;

	private UserSignupFragment fragment;
	
	public UserSignupAsyncTask(UserSignupFragment fragment, String email, String password, String firstName, String lastName){		
		this.fragment = fragment;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
			fragment.getMessageView().setText("Sign up failed. Please try it again.");
			fragment.getMessageView().setTextColor(Color.RED);
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		//TODO:  use correct username password
		user = WillGiveLoginUtils.postToSignup(email, password, firstName, lastName);
		if(user != null)     
			return true;
		
		return false;
	}

}
