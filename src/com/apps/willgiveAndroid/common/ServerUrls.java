package com.apps.willgiveAndroid.common;

public class ServerUrls {
	
	//Service URLs
	public static String HOST_URL = "http://192.168.0.103";  //my mac ip for now
	
	public static String CHARITY_PROFILE_PICTURE_PATH_PREFIX = "/resources/recipients/profilePicture/pp_";

	public static String ALL_CHARITIES_PATH = "/services/charities/listAllCharity";
	public static String GET_CHARITY_BY_ID_PATH = "/services/charityById/";
	
	public static String GET_USER_TRANSACTIONS_PATH= "/services/user/getTransactionHistory";
	public static String GET_USER_SETTINGS_PATH = "/services/user/settings";
	
	public static String GET_USER_FAV_CHARITIES_PATH = "/services/user/getFavoriteCharity";
	public static String SET_USER_FAV_CHARITY_PATH = "/services/user/setFavoriteCharity";
	
	//Login and Logout Services
	public static String LOG_OUT_PATH = "/login/logout";
	public static String MOBILE_LOGIN_PATH = "/services/login/mobileSignin";
	
}
