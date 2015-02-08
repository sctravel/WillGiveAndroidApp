package com.apps.willgiveAndroid.charity;

import java.util.List;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class RetrieveAllCharityAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private List<Charity> charityList;
	private WillGiveMainPageActivity activity;
	
	public RetrieveAllCharityAsyncTask(WillGiveMainPageActivity activity){		
		this.activity =activity; 
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
					
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		charityList = WillGiveCharityUtils.getAllCharities();
		return true;
	}

}
