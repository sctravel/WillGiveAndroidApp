package com.apps.willgiveAndroid;



import com.apps.willgiveAndroid.charity.RetrieveAllCharityAsyncTask;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;
import com.apps.willgiveAndroid.login.UserLoginAsyncTask;
import com.apps.willgiveAndroid.login.WillGiveLoginActivity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class WillGiveMainPageActivity  extends FragmentActivity {

	//private ActionBar actionBar;
    private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_bottom_tab);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
       
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.ic_action_call)),
                ScanFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Charity", getResources().getDrawable(R.drawable.ic_action_cut)),
                ScanFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("My Fav", getResources().getDrawable(R.drawable.ic_action_mail)),
                HotCharityFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Settings", getResources().getDrawable(R.drawable.ic_action_refresh)),
                HotCharityFragment.class, null);
        
        (new RetrieveAllCharityAsyncTask(WillGiveMainPageActivity.this)).execute(getApplicationContext());
        //(new UserLoginAsyncTask( new WillGiveLoginActivity() )).execute(getApplicationContext());

	}

	
	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    // ALT+ENTER
	    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	    intent.putExtra("SCAN_MODE", "PRODUCT_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
	    intent.putExtra("SAVE_HISTORY", true);//this stops saving ur barcode in barcode scanner app's history
	    startActivityForResult(intent, 0);
	}*/

	/*
	 * 
		(non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		Log.d("onResult", "got result");
		//String contents = null;
	   // super.onActivityResult(requestCode, resultCode, intent);
	  /*  if (requestCode == 0) {
	          if (resultCode == RESULT_OK) {
	        	  String scanContent = intent.getStringExtra("SCAN_RESULT");
	             String scanFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
	             formatTxt.setText("FORMAT: " + scanFormat);
	 			contentTxt.setText("CONTENT: " + scanContent);
	             // Handle successful scan
	          } else if (resultCode == RESULT_CANCELED) {
	             // Handle cancel
	        	  Toast toast = Toast.makeText(getApplicationContext(), 
				            "No scan data received!", Toast.LENGTH_SHORT);
				        toast.show();
	          }
	    }*/
		/*if (scanningResult != null) {
			//we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			formatTxt.setText("FORMAT: " + scanFormat);
			contentTxt.setText("CONTENT: " + scanContent);
		}
		else{    
			Toast toast = Toast.makeText(getApplicationContext(), 
			            "No scan data received!", Toast.LENGTH_SHORT);
			        toast.show();
	    }
		//retrieve scan result
		finishActivity(requestCode);*/
	}


	
}
