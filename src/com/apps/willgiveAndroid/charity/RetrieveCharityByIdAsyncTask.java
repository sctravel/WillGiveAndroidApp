package com.apps.willgiveAndroid.charity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;

public class RetrieveCharityByIdAsyncTask extends AsyncTask<Context, Integer, Boolean>{
		
	private WillGiveMainPageActivity activity;
	//private FragmentCallback fragmentCallback;
	private Charity charity;

	private String charityId;
	
	public RetrieveCharityByIdAsyncTask( WillGiveMainPageActivity activity, String charityId ){		
		this.activity =activity; 
		this.charityId = charityId;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Intent intent = new Intent(activity, CharityDetailPageActivity.class);
		intent.putExtra("charity", charity);
		intent.putExtra("user", activity.getUser());
		activity.startActivity(intent);
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		charity = WillGiveCharityUtils.getCharityById(charityId);
		if(charity == null) {
			return false;
		}
		return true;
	}
}
