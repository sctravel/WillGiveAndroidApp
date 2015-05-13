package com.apps.willgiveAndroid.user;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.charity.Charity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewUserSettingsActivity extends Activity {
	private User user;
	private UserSettings settings;
	private static final String TAG = "ViewUserSettingsActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.view_user_settings);
        Bundle extras = getIntent().getExtras();
        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  

	        if( user == null ) {
	        	Log.e(TAG, "user from Intent is null");
	        	this.finish();
	        }

        }  else {
        	Log.e(TAG, "Bundle from Intent is null");
        	this.finish();
        }
        setTitle("User Settings");
        settings = WillGiveUserUtils.getUserSettingsFromPreferences(getApplicationContext());
        updateSettingsView();
	}
	
	private void updateSettingsView() {
		if(settings == null) return;
		
		TextView defaultAmountPerPayView = (TextView) findViewById(R.id.defaultAmountPerPayValue);
		defaultAmountPerPayView.setText("$"+settings.getDefaultAmountPerTime());
		TextView maxAmountPerPayView = (TextView) findViewById(R.id.maxAmountPerPayValue);
		maxAmountPerPayView.setText("$"+settings.getMaxAmountPerTime());
		TextView maxAmountDailyView = (TextView) findViewById(R.id.maxAmountDailyValue);
		maxAmountDailyView.setText("$"+settings.getMaxAmountDaily());
		
		TextView recieveEmailUpdateView = (TextView) findViewById(R.id.receiveEmailUpdatesValue);
		recieveEmailUpdateView.setText("Y");
		TextView recieveEmailNotificationView = (TextView) findViewById(R.id.receiveEmailNotificationValue);
		recieveEmailNotificationView.setText("Y");
		TextView allowTransactionPublicView = (TextView) findViewById(R.id.allowTransactionPublicValue);
		allowTransactionPublicView.setText("Y");

	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            	case android.R.id.home:
            		Log.i("Action bar", "Up button pressed");
                    this.finish(); 

            }
            return true;
    }
}
