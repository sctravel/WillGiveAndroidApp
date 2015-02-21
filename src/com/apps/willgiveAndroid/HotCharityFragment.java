package com.apps.willgiveAndroid;


import java.util.List;

import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.charity.CharityListArrayAdapter;
import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;

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

public class HotCharityFragment extends Fragment {

    private String tab;
    private int color;

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
        
        ( new RetrieveAllCharityAsyncTask( new FragmentCallback(){
	        	@Override
	            public void onTaskDone() {
	        		loadHotCharityPage( listView, ( (WillGiveMainPageActivity) getActivity()).getCharityList(), context );
	            }
        	}, (WillGiveMainPageActivity) getActivity())
        ).execute(context);
        
       
       
        return listView;
    }
    
    public void loadHotCharityPage(final ListView listView, List<Charity> charityList, final Context context) {
    	
    	
    	//LayoutParams lpbt = new LayoutParams((LayoutParams.MATCH_PARENT), (LayoutParams.WRAP_CONTENT));
		//lpbt.gravity= Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL; 
		//listView.setBackgroundResource(R.drawable.background); //setBackgroundColor(Color.BLUE);
		//listView.setLayoutParams(lpbt);
        
	   /* View listHeader = context.getLayoutInflater().inflate(R.layout.course_list_header, null);
	    listHeader.setClickable(false);
	    listView.addHeaderView(listHeader);	*/
	    
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
     			  Toast.makeText(context, "This is charity ", Toast.LENGTH_LONG).show();
     			  
     			  Intent intent = new Intent(getActivity(), CharityDetailPageActivity.class); 
     			  intent.putExtra("charity", charity);
     			  intent.putExtra("user", ( (WillGiveMainPageActivity) getActivity()).getUser() );
     			  getActivity().startActivity(intent);
                  //beginExam(view, courseMeta, moduleId, emd.getExamId());
              }

         });             
        //return listView;
    }
    
    public interface FragmentCallback {
        public void onTaskDone();
    }
}
