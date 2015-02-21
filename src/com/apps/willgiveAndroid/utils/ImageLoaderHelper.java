package com.apps.willgiveAndroid.utils;

import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderHelper {
	
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.memoryCache(new LruMemoryCache(5 * 1024 * 1024))
		.memoryCacheSize(5 * 1024 * 1024)
		.diskCacheSize(50 * 1024 * 1024)
		.diskCacheFileCount(100)
	    .build();
	    ImageLoader.getInstance().init(config);
	}
}
