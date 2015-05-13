package com.apps.willgiveAndroid;


import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.zbar.Symbol;

import com.apps.willgiveAndroid.charity.CharityQRCode;
import com.apps.willgiveAndroid.charity.RetrieveCharityByEINAsyncTask;
import com.apps.willgiveAndroid.charity.RetrieveCharityByIdAsyncTask;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;
import com.apps.willgiveAndroid.common.Constants;
import com.apps.willgiveAndroid.utils.GPSTracker;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.logentries.android.AndroidLogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanFragment extends Fragment {

    private static final int ZBAR_SCANNER_REQUEST = 0;
	AndroidLogger logger ; 

    private Button scanBtn;
    private GPSTracker mGPS; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_fragment, container,false);
        logger = AndroidLogger.getLogger(getActivity().getApplicationContext(), Constants.ANDROID_LOG_UUID, false);
        mGPS = new GPSTracker(getActivity().getApplicationContext());
        scanBtn = (Button) view.findViewById(R.id.scan_button);
		
		scanBtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.scan_button){
					Intent intent = new Intent(getActivity(), ZBarScannerActivity.class);
					intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});

			        startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
			        
				}			
			}
		});
		
		
        return view;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if(requestCode == ZBAR_SCANNER_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				String scanContent = intent.getStringExtra(ZBarConstants.SCAN_RESULT);
				logger.info("scanContent - " + scanContent);

				CharityQRCode qrcode = WillGiveCharityUtils.parseCharityQRCode(scanContent, getActivity().getApplicationContext());
				if( qrcode == null ) {
					Toast toast = Toast.makeText( getActivity().getApplicationContext(), 
					        "This is not a valid WillGive QR code!", Toast.LENGTH_LONG);
					        toast.show();
				} else {
					
					if(mGPS.canGetLocation()){
			            Location location = mGPS.getLocation();
			            //Log.d("GPS", "Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude()+"; Location-"+location.getLatitude()+":"+location.getLongitude());
			            Toast.makeText(getActivity().getApplicationContext(), "Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude(), 5) ;
			        }else{
			        	Toast.makeText(getActivity().getApplicationContext(), "Please enable your location service.",5);			            
			        }
					AsyncTask<Context, Integer, Boolean> task = new RetrieveCharityByEINAsyncTask( 
							(WillGiveMainPageActivity) getActivity(), qrcode.getEIN(), mGPS.getLocation());
		 		    task.execute(getActivity().getApplicationContext());
				}
	            	 			
	         } else  {
	             // Handle cancel or failure
	        	  Toast toast = Toast.makeText( getActivity().getApplicationContext(), 
				        "No scan data received!", Toast.LENGTH_LONG);
				        toast.show();
	          }
		}
		
		//finishActivity(requestCode);
	}
    
    
}
