package com.apps.willgiveAndroid.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.apps.willgiveAndroid.user.User;

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
			fragment.getMessageView().setText("Email or password is incorrect.");
			fragment.getMessageView().setTextColor(Color.RED);
		}
		
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		//TODO:  use correct username password
		user = WillGiveLoginUtils.FacebookLogin(accessToken, refreshToken);
		if(user != null)     
			return true;
		
		return false;
	}

}
