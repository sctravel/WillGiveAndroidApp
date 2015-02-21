package com.apps.willgiveAndroid.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.apps.willgiveAndroid.R;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserLoginFragment extends Fragment {
	// Will add FB login later
    private LoginButton fbLoginBtn;

    private Button loginButton;
    private EditText usernameView;
    private EditText passwordView;    
    private TextView messageView;
    private TextView registerView;
    
    private AsyncTask<Context, Integer, Boolean> loginTask;
    
    private static final String TAG = "WillGiveLoginActivity";

    
    public TextView getMessageView() {
    	return messageView;
    }

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.willgive_login, container, false);

        uiHelper = new UiLifecycleHelper(getActivity(), statusCallback);
        uiHelper.onCreate(savedInstanceState);
  
        loginButton = (Button) view.findViewById(R.id.loginButton);
        usernameView = (EditText) view.findViewById(R.id.loginUserNameInput);
        passwordView = (EditText) view.findViewById(R.id.loginPasswordInput);
        messageView = (TextView) view.findViewById(R.id.messageView);
        registerView = (TextView) view.findViewById(R.id.link_to_register);
        
        registerView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Fragment fragment = new UserSignupFragment();
           	  	FragmentManager fm = getActivity().getSupportFragmentManager();
           	  	FragmentTransaction transaction = fm.beginTransaction();
           	  	transaction.replace(R.id.contentFragment, fragment);
           	  	transaction.commit();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	messageView.setText("");
            	String username = usernameView.getText().toString();
            	String password = passwordView.getText().toString();
            	if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() ) {

            	    //Prompt for username and password
            		//loginPage();
            		messageView.setText("Email or password can not be empty");
            		messageView.setTextColor(Color.RED);
            	} else {
                    loginTask = new UserLoginAsyncTask( UserLoginFragment.this, username, password);
            		loginTask.execute(getActivity().getApplicationContext());
            		//TODO: direct download using the stored username password
            		//loginPage();
            	}
            	
            }
        });	
        
        fbLoginBtn = (LoginButton) view.findViewById(R.id.fb_login_button);
        fbLoginBtn.setReadPermissions(Arrays.asList("email", "public_profile"));
        //Need to change to on click
        fbLoginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                	//Getting the token, then post to server to login
                    Session session = Session.getActiveSession();
                    (new FacebookLoginAsyncTask( UserLoginFragment.this, session.getAccessToken(), null) ).execute(getActivity().getApplicationContext());
                	//messageView.setText("You are currently logged in as " + user.getName());
                } else {
                	//messageView.setText("You are not logged in.");
                }
            }
        });
        
        return view;
    }	     

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                .setPermissions(Arrays.asList("public_profile"))
                .setCallback(statusCallback));
        } else {
            Session.openActiveSession(getActivity(), true, statusCallback);
        }
    }
     
    @Override
    public void onResume() {
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
               (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }
 
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();

	}
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }
 
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }
}