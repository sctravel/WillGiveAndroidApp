package com.apps.willgiveAndroid.charity;

import java.util.List;

import com.apps.willgiveAndroid.FavCharityFragment;
import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchCharityAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	
	private ListView listView;
	private SearchCharityActivity activity;
	private String keyword;
	
	public SearchCharityAsyncTask(SearchCharityActivity activity, ListView listView, String keyword){		
		this.activity =activity; 
		this.keyword = keyword;
		this.listView = listView;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		loadSearchResults(listView, activity.getCharityList(), activity );
	}
	
	public void loadSearchResults(final ListView listView, List<Charity> charityList, final SearchCharityActivity activity) {
    	
	    
		ArrayAdapter<Charity> adapter = new CharityListArrayAdapter(activity.getApplicationContext(),
	              R.layout.charity_list_item, charityList);    
        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
            listView.setOnItemClickListener(new OnItemClickListener() {
 
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view,
                     int position, long id) {
                     // ListView Clicked item value
            	  Charity charity = (Charity) listView.getItemAtPosition(position);
     			  //Toast.makeText(activity.getApplicationContext(), "This is charity ", Toast.LENGTH_LONG).show();
     			  
     			  Intent intent = new Intent(activity, CharityDetailPageActivity.class); 
     			  intent.putExtra("charity", charity);
     			  intent.putExtra("user", activity.getUser() );
     			  activity.startActivity(intent);
              }

         });             
        //return listView;
    }
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		List<Charity> charityList = WillGiveCharityUtils.searchCharityByKeyword(keyword, context);
		activity.setCharityList(charityList);
		return true;
	}

}
