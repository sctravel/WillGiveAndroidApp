package com.apps.willgiveAndroid.login;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.R.id;
import com.apps.willgiveAndroid.R.layout;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class WillGiveLoginActivity extends Activity {

	// Will add FB login later
    private LoginButton fbLoginBtn;

    private Button loginButton;
    private EditText usernameView;
    private EditText passwordView;    
    private TextView messageView;
    
    private AsyncTask<Context, Integer, Boolean> loginTask;
    
    public TextView getMessageView() {
    	return messageView;
    }

    private UiLifecycleHelper uiHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //uiHelper = new UiLifecycleHelper(this, statusCallback);
        //uiHelper.onCreate(savedInstanceState);
 
        setContentView(R.layout.willgive_login);
 
        loginButton = (Button) findViewById(R.id.loginButton);
        usernameView = (EditText) findViewById(R.id.loginUserNameInput);
        passwordView = (EditText) findViewById(R.id.loginPasswordInput);
        messageView = (TextView) findViewById(R.id.messageView);
        
        WillGiveLoginActivity activity = WillGiveLoginActivity.this;
        Log.d("!!!!!!!!!!!!!!!!!!!!", activity.getPackageName());
        loginTask = new UserLoginAsyncTask(activity);
        
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	messageView.setText("");
            	//if (usernameView.getText().toString().isEmpty() || passwordView.getText().toString().isEmpty()) {
            	if (usernameView.getText().toString() == null || passwordView.getText().toString() == null) {

            	    //Prompt for username and password
            		//loginPage();
            		messageView.setText("Email or password can not be empty");
            		messageView.setTextColor(Color.RED);
            	} else {
            		loginTask.execute(getApplicationContext());
            		//TODO: direct download using the stored username password
            		//loginPage();
            	}
            	
            }
        });	
        /*
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        loginBtn.setReadPermissions(Arrays.asList("email"));
        loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    username.setText("You are currently logged in as " + user.getName());
                } else {
                    username.setText("You are not logged in.");
                }
            }
        });*/
    }	     

   
     
    @Override
    public void onResume() {
        super.onResume();
       // uiHelper.onResume();
    }
 
    @Override
    public void onPause() {
        super.onPause();
        //uiHelper.onPause();

	}
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        //uiHelper.onDestroy();
    }
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //uiHelper.onActivityResult(requestCode, resultCode, data);
    }
 
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        //uiHelper.onSaveInstanceState(savedState);
    }
}
