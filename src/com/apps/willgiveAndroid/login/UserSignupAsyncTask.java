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
			//Call the login task here. 
			//TODO: Somehow directly signin in server side can not keep session, need to figure it out why
			// Inorder to keep login history in server side, we'd better to do this
			AsyncTask<Context, Integer, Boolean> loginTask = new UserLoginAsyncTask( fragment, email, password);
    		loginTask.execute(fragment.getActivity().getApplicationContext());
			/*
		    Intent intent = new Intent(fragment.getActivity(), com.apps.willgiveAndroid.WillGiveMainPageActivity.class);
		    intent.putExtra("user", user);    
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    fragment.getActivity().startActivity(intent);
		    fragment.getActivity().finish();*/
		} else {
			fragment.signUpFailedUIUpdate();
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		//TODO:  use correct username password
		user = WillGiveLoginUtils.postToSignup(email, password, firstName, lastName, context);
		if(user != null) {    
				//Syncing the settings once user logged in 
			SharedPreferences userCredentialPref = fragment.getActivity().getSharedPreferences(Constants.USER_CREDENTIALS_PREF_NAME, 0);
			SharedPreferences.Editor editor = userCredentialPref.edit();
		    editor.putString("email", email);
		    editor.putString("password", password);
		    editor.putString("provider", Constants.WILLGIVE_LOGIN_PROVIDER_WILLGIVE);
		    
		    // Commit the edits!
		    editor.commit();		
			return true;		
		}
		
		return false;
	}

}
