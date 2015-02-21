package com.apps.willgiveAndroid;



import java.util.List;

import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;
import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.login.UserLoginAsyncTask;
import com.apps.willgiveAndroid.login.WillGiveLoginActivity;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.ImageLoaderHelper;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WillGiveMainPageActivity  extends FragmentActivity {

	//private ActionBar actionBar;
    private FragmentTabHost mTabHost;
    private List<Charity> charityList;
    private User user; 
    
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        
       // MenuItem shareItem = menu.findItem(R.id.action_share);
       // mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
       // mShareActionProvider.setShareIntent(getDefaultIntent());
        
       // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
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
        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  
	        Log.d("UserEmail", user.getEmail());
	        Log.d("UserId", user.getId()+"");

        }  
        setTitle("Hi, "+user.getFirstName());
        
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderHelper.initImageLoader(getApplicationContext());
        
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
       
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.ic_action_call)),
                ScanFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Charity", getResources().getDrawable(R.drawable.ic_action_cut)),
                HotCharityFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("My Fav", getResources().getDrawable(R.drawable.ic_action_mail)),
                FavCharityFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Settings", getResources().getDrawable(R.drawable.ic_action_refresh)),
                HotCharityFragment.class, null);
        

	}

	
}
