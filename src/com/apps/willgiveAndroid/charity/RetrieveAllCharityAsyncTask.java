package com.apps.willgiveAndroid.charity;

import java.util.List;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.utils.FragmentCallback;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class RetrieveAllCharityAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private WillGiveMainPageActivity activity;
	private FragmentCallback fragmentCallback;
	
	public RetrieveAllCharityAsyncTask(FragmentCallback fragmentCallback, WillGiveMainPageActivity activity){		
		this.activity =activity; 
		this.fragmentCallback = fragmentCallback;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		fragmentCallback.onTaskDone();
					
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		List<Charity> existingCharityList = activity.getCharityList();
		List<Charity> charityList = WillGiveCharityUtils.getAllCharities(existingCharityList.size(), Constants.NUM_CHARITIES_PER_PULL, context);
		if(charityList == null) return false;
		
		existingCharityList.addAll(charityList);
		//activity.setCharityList(charityList);
		return true;
	}

}
