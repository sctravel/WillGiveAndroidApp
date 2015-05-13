package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.apps.willgiveAndroid.FavCharityFragment;
import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.charity.CharityListArrayAdapter;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;

public class RetrieveUserFavorateCharitiesAsyncTask  extends AsyncTask<Context, Integer, Boolean>{
	
	private FavCharityFragment fragment;
	//private FragmentCallback fragmentCallback;
	private List<Charity> favCharityList;
	private ListView listView;
		
	public RetrieveUserFavorateCharitiesAsyncTask( FavCharityFragment fragment, ListView listView){		
		this.fragment = fragment; 
		this.listView = listView;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		loadHotCharityPage(listView, favCharityList, fragment.getActivity().getApplicationContext() );
	}
	
	public void loadHotCharityPage(final ListView listView, List<Charity> charityList, final Context context) {
    	
	    
		ArrayAdapter<Charity> adapter = new CharityListArrayAdapter(context,
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
     			  
     			  Intent intent = new Intent(fragment.getActivity(), CharityDetailPageActivity.class); 
     			  intent.putExtra("charity", charity);
     			  intent.putExtra("user", ( (WillGiveMainPageActivity) fragment.getActivity()).getUser() );
     			  fragment.getActivity().startActivity(intent);
              }

         });             
        //return listView;
    }
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		favCharityList = WillGiveUserUtils.getUserFavorateCharityList();
		if(favCharityList == null || favCharityList.isEmpty()) {
			return false;
		}
		return true;
	}
}
