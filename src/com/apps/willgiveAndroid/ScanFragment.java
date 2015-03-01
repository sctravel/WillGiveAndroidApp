package com.apps.willgiveAndroid;


import java.net.MalformedURLException;
import java.net.URL;

import net.sourceforge.zbar.Symbol;

import com.apps.willgiveAndroid.charity.CharityQRCode;
import com.apps.willgiveAndroid.charity.RetrieveCharityByEINAsyncTask;
import com.apps.willgiveAndroid.charity.RetrieveCharityByIdAsyncTask;
import com.apps.willgiveAndroid.charity.WillGiveCharityUtils;
import com.apps.willgiveAndroid.common.Constants;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    private Button scanBtn;
    private TextView formatTxt;
    private TextView contentTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
       
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_fragment, container,false);
        
        
        scanBtn = (Button) view.findViewById(R.id.scan_button);
		formatTxt = (TextView) view.findViewById(R.id.scan_format);
		contentTxt = (TextView) view.findViewById(R.id.scan_content);
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
		Log.d("onResult", "got result");
		
		if(requestCode == ZBAR_SCANNER_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				String scanContent = intent.getStringExtra(ZBarConstants.SCAN_RESULT);
				Log.d("scanContent", scanContent);

				CharityQRCode qrcode = WillGiveCharityUtils.parseCharityQRCode(scanContent);
				if( qrcode == null ) {
					Toast toast = Toast.makeText( getActivity().getApplicationContext(), 
					        "This is not a valid WillGive QR code!", Toast.LENGTH_LONG);
					        toast.show();
				} else {
					
					AsyncTask<Context, Integer, Boolean> task = new RetrieveCharityByEINAsyncTask( (WillGiveMainPageActivity) getActivity(), qrcode.getEIN());
		 		    task.execute(getActivity().getApplicationContext());
				}
	             //Currently content is charityId
	             //formatTxt.setText("FORMAT: " + scanFormat);
	 			 contentTxt.setText("CONTENT: " + scanContent);			
	 			 //scanContent = "900000005";
	 			 //
	             // Handle successful scan
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
