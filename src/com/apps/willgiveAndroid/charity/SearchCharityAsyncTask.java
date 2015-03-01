package com.apps.willgiveAndroid.charity;

import java.util.List;

import com.apps.willgiveAndroid.HotCharityFragment.FragmentCallback;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class SearchCharityAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private WillGiveMainPageActivity activity;
	private String keyword;
	
	public SearchCharityAsyncTask(WillGiveMainPageActivity activity, String keyword){		
		this.activity =activity; 
		this.keyword = keyword;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);					
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		List<Charity> charityList = WillGiveCharityUtils.searchCharityByKeyword(keyword);
		activity.setCharityList(charityList);
		return true;
	}

}
