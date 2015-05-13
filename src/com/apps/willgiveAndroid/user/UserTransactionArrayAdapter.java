package com.apps.willgiveAndroid.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apps.willgiveAndroid.R;
import com.apps.willgiveAndroid.common.ServerUrls;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserTransactionArrayAdapter extends ArrayAdapter<UserTransaction>{
	private final Context context;
	private final List<UserTransaction> transactionList;
	private final int layoutId;
		
	public UserTransactionArrayAdapter(Context context, int layoutId, List<UserTransaction> transactionList){
		super(context, layoutId, transactionList);
		this.layoutId = layoutId;
	    this.context = context;
	    this.transactionList = transactionList;	
	}
	public UserTransactionArrayAdapter(Context context, int layoutId, UserTransaction[] transactions) {
	    super(context, layoutId, transactions);
	    this.layoutId = layoutId;
	    this.context = context;
	    this.transactionList = Arrays.asList(transactions);	
	}

  

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(this.layoutId, parent, false);
	    //final TextView confirmNumberTextView = (TextView) rowView.findViewById(R.id.confirmNumberTextView);
	    final TextView transactionDateTextView = (TextView) rowView.findViewById(R.id.transactionDateTextView);
	    final TextView transactionAmountTextView = (TextView) rowView.findViewById(R.id.transactionAmountTextView);
	    final TextView recipientNameTextView = (TextView) rowView.findViewById(R.id.recipientNameTextView);
	    final TextView transactionStatusTextView = (TextView) rowView.findViewById(R.id.transactionStatusTextView);

	    UserTransaction transaction = transactionList.get(position);
	    //confirmNumberTextView.setText(transaction.getConfirmationCode());
	    transactionDateTextView.setText(transaction.getDateTime().substring(0,10));
	    transactionAmountTextView.setText("$"+transaction.getAmount());
	    recipientNameTextView.setText(transaction.getRecipientName());
	    transactionStatusTextView.setText(transaction.getStatus());
	 
	    
	    return rowView;
	  
	 } 
}
