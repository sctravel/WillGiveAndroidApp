package com.apps.willgiveAndroid.login;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.R.id;
import com.apps.willgiveAndroid.R.layout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		
	  Fragment fragment = new UserLoginFragment();
	  FragmentManager fm = getSupportFragmentManager();
	  FragmentTransaction transaction = fm.beginTransaction();
	  transaction.replace(R.id.contentFragment, fragment);
	  transaction.commit();
	}
}
