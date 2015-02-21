package com.apps.willgiveAndroid.charity;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CharityDetailPageActivity extends Activity{
	
	public final static String TAG = "CharityDetailPageActivity";
	
	private Charity charity;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.charity_detail_page);
        Bundle extras = getIntent().getExtras();
        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  
        	charity =  (Charity) extras.get("charity");  

	        if( charity == null || user == null ) {
	        	Log.e(TAG, "charity or user from Intent is null");
	        	this.finish();
	        }

        }  else {
        	Log.e(TAG, "Bundle from Intent is null");
        	this.finish();
        }
        setTitle("Hi, "+user.getFirstName());
        ImageLoaderHelper.initImageLoader(getApplicationContext());

        
        //Need to make sure charity is not null
        TextView charityNameView = (TextView) findViewById(R.id.charityName);
        charityNameView.setText(charity.getName());
        TextView charityMission = (TextView) findViewById(R.id.charityMission);
        charityMission.setText(charity.getMission());
        Log.d("Charity Mission", charity.getMission());
        ImageView charityImageView = (ImageView) findViewById(R.id.charityProfilePicture);
	    String charityPicturePath = ServerUrls.HOST_URL + ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX + charity.getId();
        ImageLoader.getInstance().displayImage(charityPicturePath, charityImageView);
        
        //int height = getResources().getDisplayMetrics().heightPixels;
        //int width = getResources().getDisplayMetrics().widthPixels;

        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) charityImageView.getLayoutParams();
        //params.height = height / 2;
        //params.width = width;
        //charityImageView.setLayoutParams(params);

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
}
