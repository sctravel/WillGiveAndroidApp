package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;

public class RetrieveUserTransactionHistoryAsyncTask extends AsyncTask<Context, Integer, Boolean> {
	private List<UserTransaction> transactionList;
	private WillGiveMainPageActivity activity;
	
	public RetrieveUserTransactionHistoryAsyncTask(WillGiveMainPageActivity activity){		
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
		
		Long userId = activity.getUser().getId();

		//Service call to get user settings
		transactionList = WillGiveUserUtils.getUserTransactionHistory(userId);
			   
	    return true;
	}
}
