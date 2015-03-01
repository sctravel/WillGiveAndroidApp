package com.apps.willgiveAndroid.charity;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.user.User;

public class SearchCharityActivity extends Activity {
	
	private List<Charity> charityList;
    private User user; 
    private String query;
    
    public void setCharityList( List<Charity> list) {
    	this.charityList = list;
    }
    public List<Charity> getCharityList() {
    	return this.charityList;
    }
    public User getUser() {
    	return user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        Log.w("SearchCharityActivity","entering SearchCharityActivity");
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
   	@Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu items for use in the action bar
           MenuInflater inflater = getMenuInflater();
           inflater.inflate(R.menu.main_activity_menu, menu);
           MenuItem searchItem = menu.findItem(R.id.action_search);
           SearchView mSearchView = (SearchView) searchItem.getActionView();
           if(mSearchView==null) {
           		Log.w("search", "searchView is null");
           } else {
	   	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	   	        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	   	        mSearchView.setQueryHint("keyword");
	   	        mSearchView.setSearchableInfo(info);
	   	        mSearchView.setIconifiedByDefault(false);
           }

           // Configure the search info and add any event listeners
           
           //We need to enable sharing with FB and twitter, or only FB
           //MenuItem shareItem = menu.findItem(R.id.action_share);
           //ShareActionProvider mShareActionProvider =  (ShareActionProvider) shareItem.getActionProvider();
           //mShareActionProvider.setShareIntent(getDefaultIntent());
           
           
          // When using the support library, the setOnActionExpandListener() method is
           // static and accepts the MenuItem object as an argument
           MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener() {
               @Override
               public boolean onMenuItemActionCollapse(MenuItem item) {
                   // Do something when collapsed
               	Log.w("Search", "onMenuItemActionCollapse");
                   return true;  // Return true to collapse action view
               }

               @Override
               public boolean onMenuItemActionExpand(MenuItem item) {
                   // Do something when expanded
               	Log.w("Search", "onMenuItemActionExpand");

                   return true;  // Return true to expand action view
               }
           });
           return super.onCreateOptionsMenu(menu);
       }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            Log.w("query", query==null? "null" : query);
            
            
            //use the query to search your data somehow
        }
    }
    
    public void loadSearchResultsPage(final ListView listView, List<Charity> charityList, final Context context) {
    	
    	
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
     			  
     			  Intent intent = new Intent(SearchCharityActivity.this, CharityDetailPageActivity.class); 
     			  intent.putExtra("charity", charity);
     			  //intent.putExtra("user", ( SearchCharityActivity.getUser() );
     			  SearchCharityActivity.this.startActivity(intent);
                  //beginExam(view, courseMeta, moduleId, emd.getExamId());
              }

         });             
        //return listView;
    }
}
