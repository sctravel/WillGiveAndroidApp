package com.apps.willgiveAndroid.charity;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.zbar.Symbol;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.payment.UserPledgeAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.ImageLoaderHelper;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class CharityDetailPageActivity extends Activity{
	
	public final static String TAG = "CharityDetailPageActivity";
	
	private Charity charity;
	private User user;
	
	private Button pledgeButton;
	private TextView charityNameView;
	private ImageView charityImageView;
	private TextView missionView;
	
	
	
	public User getUser() {
		return this.user;
	}
	
	public Charity getCharity() {
		return this.charity;
	}
	
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
        charityNameView = (TextView) findViewById(R.id.charityName);
        charityNameView.setText(charity.getName());
        missionView = (TextView) findViewById(R.id.charityMission);
        missionView.setText(charity.getMission());
        Log.d("Charity Mission", charity.getMission());
        charityImageView = (ImageView) findViewById(R.id.charityProfilePicture);
        //use medium image 400x300
	    String charityPicturePath = ServerUrls.HOST_URL + ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX + charity.getId()+"";
        ImageLoader.getInstance().displayImage(charityPicturePath, charityImageView);

        pledgeButton = (Button) findViewById(R.id.userPledgeButton);
        pledgeButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showConfirmDialog();			
			}
        });
        //int height = getResources().getDisplayMetrics().heightPixels;
        //int width = getResources().getDisplayMetrics().widthPixels;

        //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) charityImageView.getLayoutParams();
        //params.height = height / 2;
        //params.width = width;
        //charityImageView.setLayoutParams(params);

	}
	
	/** Defines a default (dummy) share intent to initialize the action provider.
	  * However, as soon as the actual content to be used in the intent
	  * is known or changes, you must update the share intent by again calling
	  * mShareActionProvider.setShareIntent()
	  */
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}
	
	public void onShareClick(View v) {
	    Resources resources = getResources();

	    Intent emailIntent = new Intent();
	    emailIntent.setAction(Intent.ACTION_SEND);
	    // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
	    emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(resources.getString(R.string.share_email_native)));
	    emailIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));
	    emailIntent.setType("message/rfc822");

	    PackageManager pm = getPackageManager();
	    Intent sendIntent = new Intent(Intent.ACTION_SEND);     
	    sendIntent.setType("text/plain");


	    Intent openInChooser = Intent.createChooser(emailIntent, resources.getString(R.string.share_chooser_text));

	    List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
	    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();        
	    for (int i = 0; i < resInfo.size(); i++) {
	        // Extract the label, append it, and repackage it in a LabeledIntent
	        ResolveInfo ri = resInfo.get(i);
	        String packageName = ri.activityInfo.packageName;
	        if(packageName.contains("android.email")) {
	            emailIntent.setPackage(packageName);
	        } else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
	            Intent intent = new Intent();
	            intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
	            intent.setAction(Intent.ACTION_SEND);
	            intent.setType("text/plain");
	            if(packageName.contains("twitter")) {
	                intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_twitter));
	            } else if(packageName.contains("facebook")) {
	                // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
	                // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
	                // will show the <meta content ="..."> text from that page with our link in Facebook.
	                intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_facebook));
	            } else if(packageName.contains("mms")) {
	                intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_sms));
	            } else if(packageName.contains("android.gm")) {
	                intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(resources.getString(R.string.share_email_gmail)));
	                intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_email_subject));               
	                intent.setType("message/rfc822");
	            }

	            intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
	        }
	    }

	    // convert intentList to array
	    LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

	    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
	    startActivity(openInChooser);       
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.charity_detail_page_menu, menu);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        
        // Configure the search info and add any event listeners
        
        //We need to enable sharing with FB and twitter, or only FB
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider mShareActionProvider =  (ShareActionProvider) shareItem.getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultIntent());
        
       // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
       
        return super.onCreateOptionsMenu(menu);
    }
	
	private void showConfirmDialog() {
		// custom dialog
			final Dialog dialog = new Dialog(CharityDetailPageActivity.this);
			dialog.setContentView(R.layout.dialog_confirm_pledge);
			dialog.setTitle("Confirm Pledge");
			final TextView dialogMessageView = (TextView) dialog.findViewById(R.id.confirmPledgeDialogMessageTextView);
			dialogMessageView.setText("");
			final EditText amountText = (EditText) dialog.findViewById(R.id.pledgeAmountText);
			Button confirmButton = (Button) dialog.findViewById(R.id.confirmPledgeButton);
			
			confirmButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Double amount = Double.parseDouble( amountText.getText().toString());		
					AsyncTask<Context, Integer, Boolean> task = 
							new UserPledgeAsyncTask(CharityDetailPageActivity.this, amount, charity.getId(), dialog);
					task.execute(getApplicationContext());
				}
	        });

			dialog.show();
	}

}
