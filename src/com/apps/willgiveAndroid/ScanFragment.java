package com.apps.willgiveAndroid;


import android.content.Intent;
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
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");//for Qr code, its "QR_CODE_MODE" instead of "PRODUCT_MODE"
				    intent.putExtra("SAVE_HISTORY", true);//this stops saving ur barcode in barcode scanner app's history
				    startActivityForResult(intent, 0);
					//IntentIntegrator scanIntegrator = new IntentIntegrator(WillGiveMainPageActivity.this);
					//scanIntegrator.initiateScan();
				}
				
			}
		});
        return view;
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		Log.d("onResult", "got result");
		//String contents = null;
	   // super.onActivityResult(requestCode, resultCode, intent);
	    if (requestCode == 0) {
	          //if (resultCode == RESULT_OK) {
	        	 String scanContent = intent.getStringExtra("SCAN_RESULT");
	             String scanFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
	             formatTxt.setText("FORMAT: " + scanFormat);
	 			 contentTxt.setText("CONTENT: " + scanContent);
	             // Handle successful scan
	          //} else if (resultCode == RESULT_CANCELED) {
	             // Handle cancel
	        	//  Toast toast = Toast.makeText( getApplicationContext(), 
				  //          "No scan data received!", Toast.LENGTH_SHORT);
				    //    toast.show();
	         // }
	    }
		/*if (scanningResult != null) {
			//we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			formatTxt.setText("FORMAT: " + scanFormat);
			contentTxt.setText("CONTENT: " + scanContent);
		}
		else{    
			Toast toast = Toast.makeText(getApplicationContext(), 
			            "No scan data received!", Toast.LENGTH_SHORT);
			        toast.show();
	    }
		//retrieve scan result
		finishActivity(requestCode);*/
	}
}
