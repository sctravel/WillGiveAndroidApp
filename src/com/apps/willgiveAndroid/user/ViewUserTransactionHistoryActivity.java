package com.apps.willgiveAndroid.user;

import com.apps.willgiveAndroid.FavCharityFragment;
import com.apps.willgiveAndroid.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ViewUserTransactionHistoryActivity extends Activity {
	private User user;
	private static final String TAG = "ViewUserTransactionHistoryActivity";
	
	public User getUser(){
		return this.user;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        Bundle extras = getIntent().getExtras();
        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  

	        if( user == null ) {
	        	Log.e(TAG, "user from Intent is null");
	        	this.finish();
	        }

        }  else {
        	Log.e(TAG, "Bundle from Intent is null");
        	this.finish();
        }
        setTitle("Transaction History");
        
        final ListView listView = new ListView(getApplicationContext());
    	View listHeader = getLayoutInflater().inflate(R.layout.transaction_list_header, null);
	    listHeader.setClickable(false);
	    listView.addHeaderView(listHeader);

        AsyncTask<Context, Integer, Boolean> task = new RetrieveUserTransactionHistoryAsyncTask( 
        		ViewUserTransactionHistoryActivity.this, listView );
        task.execute(getApplicationContext());
    	//View listFooter = getLayoutInflater().inflate(R.layout.list_view_footer, null);
        //listView.addFooterView(listFooter);
        setContentView(listView);

        
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            	case android.R.id.home:
            		Log.i("Action bar", "Up button pressed");
                    this.finish(); 

            }
            return true;
    }
}
