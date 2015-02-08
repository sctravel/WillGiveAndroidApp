package com.apps.willgiveAndroid.utils;

public class StringUtils {
	
	public static boolean getBooleanFromYOrN(String s) {
		if( s!=null && (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")) ) {
			return true;
		} else {
			return false;
		}
	}
}
