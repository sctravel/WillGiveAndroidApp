package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.charity.Charity;

public class SetUserFavorateCharityAsyncTask  extends AsyncTask<Context, Integer, Boolean>{
	
	private WillGiveMainPageActivity activity;
	//private FragmentCallback fragmentCallback;
	private String charityId;
		
	public SetUserFavorateCharityAsyncTask( WillGiveMainPageActivity activity, String charityId){		
		this.activity =activity; 
		this.charityId = charityId;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		//TODO change the icon to already favored
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		return WillGiveUserUtils.setUserFavorateCharity(charityId, "Y");
		
	}

}
