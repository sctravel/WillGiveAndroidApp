package com.apps.willgiveAndroid.utils;

public class StringUtils {
	
	public static boolean getBooleanFromYOrN(String s) {
		if( s!=null && (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public final static boolean isValidEmail(String cs) {
	    if (cs == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(cs).matches();
	    }
	}
}
