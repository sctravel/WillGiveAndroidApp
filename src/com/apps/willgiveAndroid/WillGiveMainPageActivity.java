package com.apps.willgiveAndroid;



import java.util.ArrayList;
import java.util.List;

import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;
import com.apps.willgiveAndroid.charity.SearchCharityActivity;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;
import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.login.UserLoginAsyncTask;
import com.apps.willgiveAndroid.login.UserLogoutAsyncTask;
import com.apps.willgiveAndroid.login.WillGiveLoginActivity;
import com.apps.willgiveAndroid.user.RetrieveUserSettingsAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.ImageLoaderHelper;
import com.logentries.android.AndroidLogger;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class WillGiveMainPageActivity  extends FragmentActivity  {
	AndroidLogger logger;
	
	//private ActionBar actionBar;
    private FragmentTabHost mTabHost;
    private User user; 
    private List<Charity> charityList = new ArrayList<Charity>();
    private List<Charity> moreCharityList; 
    
    public AndroidLogger getLogger() {
    	return this.logger;
    }
    
    public List<Charity> getMoreCharityList() {
    	return moreCharityList;
    }
    
    public void setMoreCharityList(List<Charity> moreCharityList) {
    	this.moreCharityList=moreCharityList;
    }
    
    public void setCharityList( List<Charity> list) {
    	this.charityList = list;
    }
    public List<Charity> getCharityList() {
    	return this.charityList;
    }
    public User getUser() {
    	return user;
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
	        
	        //mSearchView
	        mSearchView.setSearchableInfo(info);
	        //mSearchView.setIconifiedByDefault(false);
        }   
        
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_bottom_tab);
        Bundle extras = getIntent().getExtras();
        logger = AndroidLogger.getLogger(getApplicationContext(), Constants.ANDROID_LOG_UUID, false);

        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  
	        logger.info("UserEmail - "+ user.getEmail());
	        logger.info("UserId - " + user.getId()+"");
        }  
        
        setTitle("Hi, "+user.getFirstName());
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderHelper.initImageLoader(getApplicationContext());
        //Syncing the user settings data
		AsyncTask<Context, Integer, Boolean> task = new RetrieveUserSettingsAsyncTask( this );
		task.execute(getApplicationContext());
		
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
       
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.ic_action_call)),
                ScanFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Explore", getResources().getDrawable(R.drawable.ic_action_cut)),
                HotCharityFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Fav", getResources().getDrawable(R.drawable.ic_action_mail)),
                FavCharityFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Profile", getResources().getDrawable(R.drawable.ic_action_refresh)),
                UserProfileFragment.class, null);
        

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            //openSearch();
	            return true;       
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/** Defines a default (dummy) share intent to initialize the action provider.
	  * However, as soon as the actual content to be used in the intent
	  * is known or changes, you must update the share intent by again calling
	  * mShareActionProvider.setShareIntent()
	  */
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}
	
	/*
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
    	Log.w("Search", "onQueryTextSubmit");

		return false;
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
    	Log.w("Search", "onQueryTextChange");

		return false;
	}*/
	protected boolean isAlwaysExpanded() {
        return false;
    }
	
	@Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
}
