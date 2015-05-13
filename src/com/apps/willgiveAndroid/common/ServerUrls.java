package com.apps.willgiveAndroid.common;

public class ServerUrls {
	
	//Service URLs
	public static String HOST_URL = "https://www.willgive.org";/*//"http://192.168.0.103""http://10.104.62.10"*/;  //my mac ip for now
	
	public static String CHARITY_PROFILE_PICTURE_PATH_PREFIX = "/resources/recipients/profilePicture/pp_";

	public static String ALL_CHARITIES_PATH = "/services/charities/listAllCharity";
	public static String GET_CHARITY_BY_ID_PATH = "/services/charityById/";
	public static String GET_CHARITY_BY_EIN_PATH = "/services/charityByEIN/";
	public static String SEARCH_CHARITY_PATH = "/services/charities/searchCharity";
	
	public static String GET_USER_TRANSACTIONS_PATH= "/services/user/getTransactionHistory";
	public static String GET_USER_SETTINGS_PATH = "/services/user/settings";
	
	public static String GET_USER_FAV_CHARITIES_PATH = "/services/user/getFavoriteCharity";
	public static String SET_USER_FAV_CHARITY_PATH = "/services/user/setFavoriteCharity";
	 
	//Login and Logout Services
	public static String LOG_OUT_PATH = "/login/logout";
	public static String MOBILE_LOGIN_PATH = "/services/login/mobileSignin";
	public static String MOBILE_SIGNUP_PATH = "/services/login/mobileSignup";

	public static String MOBILE_FACEBOOK_LOGIN_PATH = "/auth/facebook/token";
	public static String QR_SCAN_PATH = "/c/"; //need to append id
	
	public static String PAYMENT_USER_PLEDGE_PATH = "/services/payment/pledge";
	
	public static String CAHRITY_PAGE_URL = HOST_URL+"/charity/";
	//#120220150  #
}
