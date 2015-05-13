package com.apps.willgiveAndroid.charity;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.login.UserLoginAsyncTask;
import com.apps.willgiveAndroid.login.UserLoginFragment;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.user.WillGiveUserUtils;
import com.logentries.android.AndroidLogger;

public class SearchCharityActivity extends Activity {
	
	AndroidLogger logger;
	private List<Charity> charityList;
    private User user; 
    private String query;
    private AsyncTask<Context, Integer, Boolean> searchTask;
    private ListView listView;
    
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
        logger = AndroidLogger.getLogger(getApplicationContext(), Constants.ANDROID_LOG_UUID, false);

    	user = WillGiveUserUtils.getUserObjectFromPreferences(getApplicationContext());
    	logger.info("SearchCharityActivity User - " + user.toString());
    	listView = new ListView(getApplicationContext());
    	//View listHeader = getLayoutInflater().inflate(R.layout.charity_list_header, null);
	    //listHeader.setClickable(false);
	    //listView.addHeaderView(listHeader);
	    
        handleIntent(getIntent());
        
        setContentView(listView);
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
   	@Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu items for use in the action bar
           MenuInflater inflater = getMenuInflater();
           inflater.inflate(R.menu.main_activity_menu, menu);
           MenuItem searchItem = menu.findItem(R.id.action_search);
           final SearchView mSearchView = (SearchView) searchItem.getActionView();
           if(mSearchView==null) {
        	   logger.error("SearchView is null");
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
           
           mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
               @Override
               public boolean onSuggestionSelect(int position) {
                   return true;
               }

               @Override
               public boolean onSuggestionClick(int position) {
                   CursorAdapter selectedView = mSearchView.getSuggestionsAdapter();
                   Cursor cursor = (Cursor) selectedView.getItem(position);
                   int index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1);
                   mSearchView.setQuery(cursor.getString(index), true);
                   return true;
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
            logger.info("UserId: "+user.getId()+"; Search query: " + query==null? "null" : query);   
            
            searchTask = new SearchCharityAsyncTask( SearchCharityActivity.this, listView, query);
            searchTask.execute(getApplicationContext());
            //use the query to search your data somehow
        }
    }
    
    
}
