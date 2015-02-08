package com.apps.willgiveAndroid.utils;


import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;

//This is a hack
//Since the Expires pass by Nodejs is Invalid Date, we always set it null here
//Otherwise the cookie parsing will fail, and can't keep logged in
class LenientCookieSpec extends BrowserCompatSpec {
    public LenientCookieSpec() {
        super();
        registerAttribHandler(ClientCookie.EXPIRES_ATTR, new BasicExpiresHandler(DATE_PATTERNS) {
            @Override public void parse(SetCookie cookie, String value) throws MalformedCookieException {
                
                // You should set whatever you want in cookie
                cookie.setExpiryDate(null);
               
            }
        });
    }
}
