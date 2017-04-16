package com.example.user.humblebragwallofshame;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by user on 16-Apr-17.
 */

public class Logout {
Context context;

    Logout(Context context){
        this.context=context;

    }
    public void logout(){

        new AlertDialog.Builder(context)
                .setTitle("Are you sure you want to LOGOUT?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()     {

                    public void onClick(DialogInterface arg0, int arg1) {
                        logoutTwitter();
                    }
                }).create().show();
    }public void logoutTwitter() {
        TwitterSession twitterSession = Twitter.getInstance().core.getSessionManager().getActiveSession();
        if (twitterSession != null) {
            ClearCookies(context);
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
            Intent intent=new Intent(context,LoginActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }

    public static void ClearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}
