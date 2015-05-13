package com.apps.willgiveAndroid.login;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.R.id;
import com.apps.willgiveAndroid.R.layout;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class WillGiveLoginActivity extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.login_signup_activity);
	  
	  Bundle extras = getIntent().getExtras();
      //TODO: we also need to get marked questions' information
      if (extras != null) {
	       //exam = (Exam) extras.getSerializable("exam");
	       boolean isLoggedOut = extras.getBoolean("isLoggedOut",false);  
	       if(isLoggedOut) {
	    	   Log.d("Login", "isLoggedOut is true");
	    	   SharedPreferences userCredentialPref = getSharedPreferences(Constants.USER_CREDENTIALS_PREF_NAME, 0);
	   		   SharedPreferences.Editor editor = userCredentialPref.edit();
	   		   editor.clear();
	   		   editor.commit();
	   		   //FB logout
	   		   callFacebookLogout(getApplicationContext());
	   		   
	       }
      }  
	  
		
	  Fragment fragment = new UserLoginFragment();
	  FragmentManager fm = getSupportFragmentManager();
	  FragmentTransaction transaction = fm.beginTransaction();
	  transaction.replace(R.id.contentFragment, fragment);
	  transaction.commit();
	}
	
	/**
	 * Logout From Facebook 
	 */
	public static void callFacebookLogout(Context context) {
	    Session session = Session.getActiveSession();
	    if (session != null) {

	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	            //clear your preferences if saved
	        }
	    } else {

	        session = new Session(context);
	        Session.setActiveSession(session);

	        session.closeAndClearTokenInformation();
	            //clear your preferences if saved

	    }

	}
}
