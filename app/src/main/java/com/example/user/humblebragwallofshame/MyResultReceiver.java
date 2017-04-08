package com.example.user.humblebragwallofshame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

/**
 * Created by user on 08-Apr-17.
 */



public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            final Long tweetId = intent.getExtras().getLong(TweetUploadService.EXTRA_TWEET_ID);
            Toast.makeText(context,"Success "+tweetId,Toast.LENGTH_LONG).show();


        } else {
            // failure
            final Intent retryIntent = intent.getExtras().getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);

            Toast.makeText(context,"Failure ",Toast.LENGTH_LONG).show();
        }
    }
}
