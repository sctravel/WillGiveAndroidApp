package com.apps.willgiveAndroid.charity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;

public class RetrieveCharityByEINAsyncTask extends AsyncTask<Context, Integer, Boolean>{
		
	private WillGiveMainPageActivity activity;
	//private FragmentCallback fragmentCallback;
	private Charity charity;
	private Location location;
	private String EIN;
	
	public RetrieveCharityByEINAsyncTask( WillGiveMainPageActivity activity, String EIN, Location location ){		
		this.activity =activity; 
		this.EIN = EIN;
		this.location = location;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(charity!=null){
			Intent intent = new Intent(activity, CharityDetailPageActivity.class);
			intent.putExtra("charity", charity);
			intent.putExtra("user", activity.getUser());
			activity.startActivity(intent);
		} else {
			  Toast.makeText(activity.getApplicationContext(), "Internal error. Please try to refresh.", Toast.LENGTH_LONG).show();

		}
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		charity = WillGiveCharityUtils.getCharityByEIN(EIN, location, context);
		if(charity == null) {
			return false;
		}
		return true;
	}
}
