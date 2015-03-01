package com.apps.willgiveAndroid.payment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.R.color;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;
import com.apps.willgiveAndroid.common.Constants;

public class UserPledgeAsyncTask extends AsyncTask<Context, Integer, Boolean>{
	private CharityDetailPageActivity activity;
	private Dialog confirmDialog;
	private Double amount;
	private Long recipientId;
	
	public UserPledgeAsyncTask(CharityDetailPageActivity activity, Double amount, Long recipientId, Dialog confirmDialog){		
		this.activity =activity; 
		this.amount = amount;
		this.recipientId = recipientId;
		this.confirmDialog = confirmDialog;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);	
		if(result){
			confirmDialog.cancel();
			Toast.makeText(activity.getApplicationContext(), "Congratulations! You have successfully made a pledge.", Toast.LENGTH_LONG).show();
			
		} else {
			TextView dialogMessageView = (TextView) confirmDialog.findViewById(R.id.confirmPledgeDialogMessageTextView);
			dialogMessageView.setText("Sorry, please try it again.");
			dialogMessageView.setTextColor(Color.RED);
		}
		//notify user whether the pledge succeeded or not
		// maybe open brower for user to approve pledge
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
					
	   //Do we need to open browser to let people approve it ?
	    return WillGivePaymentUtils.sendPledge(activity.getUser().getId(), amount, recipientId);
	}
}