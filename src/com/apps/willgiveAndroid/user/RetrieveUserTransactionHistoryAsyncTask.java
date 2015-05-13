package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.charity.CharityListArrayAdapter;
import com.apps.willgiveAndroid.common.Constants;

public class RetrieveUserTransactionHistoryAsyncTask extends AsyncTask<Context, Integer, Boolean> {
	private List<UserTransaction> transactionList;
	private ViewUserTransactionHistoryActivity activity;
	private ListView listView;
	
	public RetrieveUserTransactionHistoryAsyncTask(ViewUserTransactionHistoryActivity activity, ListView listView){		
		this.activity =activity; 
		this.listView = listView;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		loadTransactionHistoryPage(listView, transactionList, activity.getApplicationContext() );
	}
	
	public void loadTransactionHistoryPage(final ListView listView, List<UserTransaction> transactionList, final Context context) {
    	
	    
		ArrayAdapter<UserTransaction> adapter = new UserTransactionArrayAdapter(context,
	              R.layout.transaction_list_item, transactionList);    
        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
                  
        //return listView;
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
