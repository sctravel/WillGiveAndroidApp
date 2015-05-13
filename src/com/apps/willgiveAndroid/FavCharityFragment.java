package com.apps.willgiveAndroid;


import java.util.List;

import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.charity.CharityListArrayAdapter;
import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;
import com.apps.willgiveAndroid.user.RetrieveUserFavorateCharitiesAsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class FavCharityFragment extends Fragment {

   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	final Context context = getActivity().getApplicationContext();
    	final ListView listView = new ListView(context);
    	View listHeader = getActivity().getLayoutInflater().inflate(R.layout.charity_list_header, null);
	    listHeader.setClickable(false);
	    listView.addHeaderView(listHeader);
	    TextView headerView = (TextView) listHeader.findViewById(R.id.charityListHeaderTextView);
	    headerView.setText("My Favorate");
	    
        ( new RetrieveUserFavorateCharitiesAsyncTask( FavCharityFragment.this, listView )
        ).execute(context);
               
        return listView;
    }
    
}
