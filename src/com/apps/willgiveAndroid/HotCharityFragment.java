package com.apps.willgiveAndroid;


import java.util.List;

import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.charity.CharityListArrayAdapter;
import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.utils.FragmentCallback;
import com.logentries.android.AndroidLogger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class HotCharityFragment extends Fragment  {
	
	AndroidLogger logger; 

    private View listFooter;
    private ArrayAdapter<Charity> adapter;
    private boolean isLoadingFlag = false;

    public boolean getIsLoadingFlag() {
    	return this.isLoadingFlag;
    }
    public void setIsLoadingFlag(boolean isLoadingFlag) {
    	this.isLoadingFlag = isLoadingFlag;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	final Context context = getActivity().getApplicationContext();
        logger = AndroidLogger.getLogger(getActivity().getApplicationContext(), Constants.ANDROID_LOG_UUID, false);

    	final ListView listView = new ListView(context);
    	View listHeader = getActivity().getLayoutInflater().inflate(R.layout.charity_list_header, null);
	    listHeader.setClickable(false);
	    listView.addHeaderView(listHeader);
	    TextView headerView = (TextView) listHeader.findViewById(R.id.charityListHeaderTextView);
	    headerView.setText("Charity Explore");
    	View listFooter = getActivity().getLayoutInflater().inflate(R.layout.list_view_footer, null);
        listView.addFooterView(listFooter);
        //( (TextView) listFooter.findViewById(R.id.listViewFooterTextView)).setText("Loading more ... ");

        ( new RetrieveAllCharityAsyncTask( new FragmentCallback(){
	        	@Override
	            public void onTaskDone() {
	        		loadHotCharityPage( listView, ( (WillGiveMainPageActivity) getActivity()).getCharityList(), context );
	            }
        	}, (WillGiveMainPageActivity) getActivity())
        ).execute(context);
        
        listView.setOnScrollListener(new OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                //Log.d("Loading", "Scrolling");

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                	if(isLoadingFlag == false) {
                    	isLoadingFlag =true;

                    	logger.info("Loading hot charities - Loading more items");
	                	( new RetrieveAllCharityAsyncTask( new FragmentCallback(){
		        	        	@Override
		        	            public void onTaskDone() {
		        	        		loadMoreItems( listView, ( (WillGiveMainPageActivity) getActivity()).getMoreCharityList(), context );
		        	            }
	                		}, (WillGiveMainPageActivity) getActivity() )).execute(context);
                	} else {
	                    logger.info("Loadinghot charities - Still in Loading, please wait");

                	}
                
                    
                }
            }
        });
       
        return listView;
    }
    
    //TODO: how to detect it reaches the end
    @SuppressWarnings("unchecked")
	public void loadMoreItems(final ListView listView, List<Charity> charityList, final Context context) {
    	
        final int positionToSave = listView.getFirstVisiblePosition();
	    //adapter.clear();
        adapter.notifyDataSetChanged();
    	//adapter.addAll(charityList); //update your adapter's data
		listView.post(new Runnable() {
		
		    @Override
		    public void run() {
		        listView.setSelection(positionToSave);
		    }
		});
    	isLoadingFlag = false;
    	
    }
    
    public void loadHotCharityPage(final ListView listView, List<Charity> charityList, final Context context) {
    	
    	
    	//LayoutParams lpbt = new LayoutParams((LayoutParams.MATCH_PARENT), (LayoutParams.WRAP_CONTENT));
		//lpbt.gravity= Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL; 
		//listView.setBackgroundResource(R.drawable.background); //setBackgroundColor(Color.BLUE);
		//listView.setLayoutParams(lpbt);
		adapter = new CharityListArrayAdapter(context,
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
        
        //( (TextView) listFooter.findViewById(R.id.listViewFooterTextView)).setText("");
        //return listView;
    }

	
    
}
