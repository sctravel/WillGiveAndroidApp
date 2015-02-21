package com.apps.willgiveAndroid.charity;

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

public class CharityListArrayAdapter extends ArrayAdapter<Charity>{
	private final Context context;
	private final List<Charity> charityList;
	private final int layoutId;
	@SuppressLint("UseSparseArrays")
	private final static Map<Long, Drawable> imageMap = new HashMap<Long, Drawable>(); //Cache the image, otherwise it'll be slow
	private final ImageLoader imageLoader;

    private ImageSize targetSize = new ImageSize(120, 100); // result Bitmap will be fit to this size
    

	private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
    .showImageOnLoading(R.drawable.launcher_icon) // resource or drawable
    .showImageForEmptyUri(R.drawable.launcher_icon) // resource or drawable
    .showImageOnFail(R.drawable.ico_account) // resource or drawable
    .resetViewBeforeLoading(false)  // default
    .delayBeforeLoading(1000)
    .build();
	
	public CharityListArrayAdapter(Context context, int layoutId, List<Charity> charityList){
		super(context, layoutId, charityList);
		this.layoutId = layoutId;
	    this.context = context;
	    this.charityList = charityList;	
	    imageLoader = ImageLoader.getInstance();
	}
	public CharityListArrayAdapter(Context context, int layoutId, Charity[] charities) {
	    super(context, layoutId, charities);
	    this.layoutId = layoutId;
	    this.context = context;
	    this.charityList = Arrays.asList(charities);	
	    imageLoader = ImageLoader.getInstance();
	}

  

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(this.layoutId, parent, false);
	    final TextView textView = (TextView) rowView.findViewById(R.id.charityListItemName);
	    final ImageView imageView = (ImageView) rowView.findViewById(R.id.charityListItemIcon);
	    Charity charity = charityList.get(position);
	    textView.setText(charity.getName());
	    
    	final long charityId = charity.getId();
	    //String charityPicturePath = ServerUrls.HOST_URL + ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX + charity.getId();
	    //imageView.setImageResource(R.drawable.icon_exam);
	    //ImageLoader.getInstance().displayImage(charityPicturePath, imageView, options);
	    if(!imageMap.containsKey(charityId)) {
    	    String charityPicturePath = ServerUrls.HOST_URL + ServerUrls.CHARITY_PROFILE_PICTURE_PATH_PREFIX + charityId;
    	    imageLoader.loadImage(charityPicturePath, targetSize, options, new SimpleImageLoadingListener() {
    	        @Override
    	        public void onLoadingComplete(String imageUri, View view, android.graphics.Bitmap loadedImage) {
    	        	Drawable verticalImage = new BitmapDrawable(context.getResources(), loadedImage);
    	        	imageMap.put(charityId, verticalImage);
    	        	imageView.setImageDrawable(verticalImage);
    	            // Do whatever you want with Bitmap
    	        }
    	    });
	    } else {
	    	imageView.setImageDrawable(imageMap.get(charity.getId()));
	    }

	    
	    return rowView;
	  
	 } 
}
