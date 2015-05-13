package com.apps.willgiveAndroid.charity;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.zbar.Symbol;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.apps.willgiveAndroid.payment.UserPledgeAsyncTask;
import com.apps.willgiveAndroid.user.SetUserFavorateCharityAsyncTask;
import com.apps.willgiveAndroid.user.User;
import com.apps.willgiveAndroid.utils.ImageLoaderHelper;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.logentries.android.AndroidLogger;
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
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
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
	AndroidLogger logger; 

	private Charity charity;
	private User user;
	private TransactionSummaryForCharity summary;
	
	private Button pledgeButton;
	private TextView charityNameView;
	private ImageView charityImageView;
	private TextView missionView;
	
	private ImageView favImageView ;
	private TextView favView;
	
	
	public TransactionSummaryForCharity getSummary() {
		return summary;
	}
	public void setSummary(TransactionSummaryForCharity summary) {
		this.summary = summary;
	}
	
	public ImageView getFavImageView() {
		return this.favImageView;
	}
	public TextView getFavView() {
		return this.favView;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Charity getCharity() {
		return this.charity;
	}
	
	public void updateTransactionSummaryView(TransactionSummaryForCharity summary) {
		if(summary==null) return;
		TextView summaryView = (TextView) findViewById(R.id.charityContributionSummary);
		String s = "Total " + summary.getTotalCount() + " supporters contributed $"+summary.getTotalAmount();
		if(summary.getDuration()!=null && summary.getDuration()>0) {
			s = s+" in recent "+summary.getDuration() +" days.";
		}
		summaryView.setText(s);
		summaryView.setTextColor(Color.GREEN);	
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.charity_detail_page);
        logger = AndroidLogger.getLogger(getApplicationContext(), Constants.ANDROID_LOG_UUID, false);
        Bundle extras = getIntent().getExtras();
        //TODO: we also need to get marked questions' information
        if (extras != null) {
	        //exam = (Exam) extras.getSerializable("exam");
	        user =  (User) extras.get("user");  
        	charity =  (Charity) extras.get("charity");  

	        if( charity == null || user == null ) {
	        	logger.error("charity or user from Intent is null");
	        	this.finish();
	        }

        }  else {
        	logger.error("Bundle from Intent is null");
        	this.finish();
        }
        setTitle("Hi, "+user.getFirstName());
        ImageLoaderHelper.initImageLoader(getApplicationContext());

        favImageView = (ImageView) findViewById(R.id.charityDetailFavIcon);
        favView = (TextView) findViewById(R.id.charityDetailFav);
        if(charity.getIsFavored()) {
        	favView.setText("Remove from favorate");
        	favImageView.setImageResource(R.drawable.fav2);
        	favView.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				AsyncTask<Context, Integer, Boolean> task = new SetUserFavorateCharityAsyncTask(CharityDetailPageActivity.this, 
    						charity.getId().toString(), "N");
    				task.execute(getApplicationContext());
    			}
    			
        	});
        } else {
        	favView.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				AsyncTask<Context, Integer, Boolean> task = new SetUserFavorateCharityAsyncTask(CharityDetailPageActivity.this, 
    						charity.getId().toString(), "Y");
    				task.execute(getApplicationContext());
    			}
        	});
        }
        final TextView addressView = (TextView) findViewById(R.id.charityDetailAddress);
        //addressView.setPaintFlags(addressView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        addressView.setText(charity.getAddress()+","+charity.getCity()+","+charity.getState()+","+charity.getZipcode());
        /*addressView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String address = addressView.getText().toString();
				if( address !=null && !address.trim().isEmpty() ) {
					Log.i(TAG, "Direction to charity's address: "+address );
					Intent i = new Intent(Intent.ACTION_CALL); //TODO: change to map
					i.setData(Uri.parse(address));
					startActivity(i);
				}
			}
		});*/
        
        TextView phoneView = (TextView) findViewById(R.id.charityDetailPhone);
        //phoneView.setPaintFlags(phoneView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        phoneView.setText(charity.getPhone());
        //May need to process phone numbers
        phoneView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = charity.getPhone();
				if( phone !=null && !phone.trim().isEmpty() ) {
					phone = phone.trim();
					logger.info("Call charity's phone: "+phone );
					Intent i = new Intent(Intent.ACTION_CALL);
					i.setData(Uri.parse("tel:"+phone ));
					startActivity(i);
				}
			}
		});
        
        
        TextView websiteView = (TextView) findViewById(R.id.charityDetailWebsite);
        //websiteView.setPaintFlags(websiteView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        websiteView.setText(charity.getWebsite());
        websiteView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = charity.getWebsite();
				if( url !=null && !url.trim().isEmpty() ) {
					url = url.trim();
					if (!url.startsWith("https://") && !url.startsWith("http://")){
					    url = "http://" + url;
					}
					logger.info("Go to charity's website: "+url );
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
					startActivity(i);
				}
			}
		});
        
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
		Intent sendIntent = new Intent(Intent.ACTION_SEND);     
	    // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
	    sendIntent.putExtra( Intent.EXTRA_TEXT, ServerUrls.CAHRITY_PAGE_URL+getCharity().getId() );
	    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share WillGive Charity Url");
	    sendIntent.setType("text/plain");
	    return sendIntent;
	}
	
	/*
	public void onShareClick(View v) {
	    Resources resources = getResources();

	    Intent sendIntent = new Intent(Intent.ACTION_SEND);     
	    // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
	    sendIntent.putExtra( Intent.EXTRA_TEXT, ServerUrls.CAHRITY_PAGE_URL+getCharity().getId() );
	    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share WillGive Charity Url");
	    sendIntent.setType("text/plain");

	    PackageManager pm = getPackageManager();
	    sendIntent.setType("text/plain");


	    Intent openInChooser = Intent.createChooser(sendIntent, resources.getString(R.string.share_chooser_text));

	    List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
	    List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();        
	    for (int i = 0; i < resInfo.size(); i++) {
	        // Extract the label, append it, and repackage it in a LabeledIntent
	        ResolveInfo ri = resInfo.get(i);
	        String packageName = ri.activityInfo.packageName;
	        if(packageName.contains("android.email")) {
	        	sendIntent.setPackage(packageName);
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

	    
	    openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray());
	    startActivity(openInChooser);       
	}*/
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            	case android.R.id.home:
            		//Log.i("Action bar", "Up button pressed");
                    this.finish(); 

            }
            return true;
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
			final EditText notesText = (EditText) dialog.findViewById(R.id.pledgeNotesText);
			Button confirmButton = (Button) dialog.findViewById(R.id.confirmPledgeButton);
			
			confirmButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Double amount = Double.parseDouble( amountText.getText().toString());	
					String notes = notesText.getText().toString();
					AsyncTask<Context, Integer, Boolean> task = 
							new UserPledgeAsyncTask(CharityDetailPageActivity.this, amount, charity.getId(), notes, dialog);
					task.execute(getApplicationContext());
				}
	        });

			dialog.show();
	}

}
