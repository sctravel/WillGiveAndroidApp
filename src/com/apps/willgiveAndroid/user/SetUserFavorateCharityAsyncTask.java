package com.apps.willgiveAndroid.user;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.WillGiveMainPageActivity;
import com.apps.willgiveAndroid.charity.Charity;
import com.apps.willgiveAndroid.charity.CharityDetailPageActivity;

public class SetUserFavorateCharityAsyncTask  extends AsyncTask<Context, Integer, Boolean>{
	
	private CharityDetailPageActivity activity;
	private String charityId;
	private String value;
		
	public SetUserFavorateCharityAsyncTask( CharityDetailPageActivity activity, String charityId, String value){		
		this.activity =activity; 
		this.charityId = charityId;
		this.value = value;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		//TODO change the icon to already favored
		if(result) {
			if(value.equalsIgnoreCase("Y")) {
				activity.getFavView().setText("Remove from favorate");
				activity.getFavImageView().setImageResource(R.drawable.fav2);
				//update the isFav value of the charity
				activity.getCharity().setIsFavored(true);
			} else {
				activity.getFavView().setText("Add to favorate");
				activity.getFavImageView().setImageResource(R.drawable.fav);
				//update the isFav value of the charity
				activity.getCharity().setIsFavored(false);
			}
			
		}
	}
	
	@Override
	protected Boolean doInBackground(Context... contexts) {
		// TODO Auto-generated method stub
		Context context = contexts[0];
		
		return WillGiveUserUtils.setUserFavorateCharity(charityId, value);
		
	}

}
