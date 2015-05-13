package com.apps.willgiveAndroid.charity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


public class RetrieveContributionSummaryForCharityAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
private CharityDetailPageActivity activity;
private Long duration;

public RetrieveContributionSummaryForCharityAsyncTask( CharityDetailPageActivity activity, Long duration ){		
	this.activity =activity; 
	this.duration = duration;
}

@Override
protected void onPostExecute(Boolean result) {
	super.onPostExecute(result);
	if(result) {
		activity.updateTransactionSummaryView(activity.getSummary());
	}
	//update UI
}

@Override
protected Boolean doInBackground(Context... contexts) {
	// TODO Auto-generated method stub
	Context context = contexts[0];
	
	TransactionSummaryForCharity summary = WillGiveCharityUtils.getTransactionSummaryForCharity(activity.getCharity().getId(), duration, context);
	if(summary == null) {
		return false;
	}
	activity.setSummary(summary);
	return true;
}
}
