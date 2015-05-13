package com.apps.willgiveAndroid.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.utils.StringUtils;
import com.facebook.widget.LoginButton;

public class UserSignupFragment extends Fragment {

    private Button signUpBtn;
    private EditText emailView;
    private EditText passwordView;
    private EditText firstNameView;
    private EditText lastNameView;
    private TextView messageView;
    private TextView signinView;
    private LoginButton fbSignupBtn;

    private AsyncTask<Context, Integer, Boolean> signupTask;

    public TextView getMessageView() {
    	return messageView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    public void signingUpUIUpdate() {
    	signUpBtn.setEnabled(false);
    	fbSignupBtn.setEnabled(false);
    	signinView.setEnabled(false);
    	messageView.setText("Signing up ... ");
    }
    
    public void signUpFailedUIUpdate() {
    	signUpBtn.setEnabled(true);
    	fbSignupBtn.setEnabled(true);
    	signinView.setEnabled(true);
    	messageView.setText("Sign up failed. Please try it again");
    	messageView.setTextColor(Color.RED);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.willgive_signup, container,false);
        signinView = (TextView) view.findViewById(R.id.link_to_login);
        messageView = (TextView) view.findViewById(R.id.reg_message);
        emailView = (EditText) view.findViewById(R.id.reg_email);
        passwordView = (EditText) view.findViewById(R.id.reg_password);
        lastNameView = (EditText) view.findViewById(R.id.reg_lastname);
        firstNameView = (EditText) view.findViewById(R.id.reg_firstname);
        signUpBtn = (Button) view.findViewById(R.id.btnRegister);
        fbSignupBtn = (LoginButton) view.findViewById(R.id.fb_signup_button);

        
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	messageView.setText("");
            	
            	String email = emailView.getText().toString();
            	String password = passwordView.getText().toString();
            	String lastName = lastNameView.getText().toString();
            	String firstName = firstNameView.getText().toString();
            	if(email.isEmpty() || !StringUtils.isValidEmail(email)) {
            		messageView.setText("Email is not valid. Please check it again.");
            		messageView.setTextColor(Color.RED);
            		return;
            	}
            	if(password.isEmpty() || password.length()<8 ) {
            		messageView.setText("The minimum length of password is 8.");
            		messageView.setTextColor(Color.RED);
            		return;
            	}
            	if(lastName.isEmpty() || firstName.isEmpty()) {
            		messageView.setText("First name or last name should not be empty.");
            		messageView.setTextColor(Color.RED);
            		return;
            	}
            	
            	signingUpUIUpdate();
            	signupTask = new UserSignupAsyncTask( UserSignupFragment.this, email, password, firstName, lastName);
            	signupTask.execute(getActivity().getApplicationContext());
            }
        });
        
        signinView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Fragment fragment = new UserLoginFragment();
           	  	FragmentManager fm = getActivity().getSupportFragmentManager();
           	  	FragmentTransaction transaction = fm.beginTransaction();
           	  	transaction.replace(R.id.contentFragment, fragment);
           	  	transaction.commit();
            }
        });
        return view;
    }
}