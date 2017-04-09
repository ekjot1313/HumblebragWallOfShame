package com.example.user.humblebragwallofshame;
// EmbeddedTweetsActivity
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.Card;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetcomposer.TweetUploadService;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.UserTimeline;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmbeddedTweets extends AppCompatActivity {
Uri uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_tweets);

       // final LinearLayout myLayout = (LinearLayout) findViewById(R.id.my_tweet_layout);

        FloatingActionButton ct=(FloatingActionButton)findViewById(R.id.composetweet);
        final ListView lv=(ListView)findViewById(R.id.lv);
       // RecyclerView rv=(RecyclerView)findViewById(R.id.rv);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);



        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               selectImage();
              //  final Card card = new Card.AppCardBuilder(EmbeddedTweets.this)
               //         .imageUri(uri)
                //        .build();
              /*  final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                final Intent intent = new ComposerActivity.Builder(EmbeddedTweets.this)
                        .session(session)
                  //      .card(card)
                        .darkTheme()
                        .createIntent();
                startActivity(intent);
*/


            }
        });


        UserTimeline timeline = new UserTimeline.Builder().maxItemsPerRequest(100).screenName("humblebrag").build();

       // SearchTimeline timeline = new SearchTimeline.Builder().query("@humblebrag").build();
        //final CollectionTimeline timeline = new CollectionTimeline.Builder().id(214680621l).build();


        final TweetAdapter timelineAdapter = new TweetAdapter(this, timeline) /*{

            @Override
            public View getView( int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                final Tweet tweet = getItem(position);
                User user=tweet.user;
                if (rowView == null) {
                    rowView = new CompactTweetView(context, tweet, R.style.tw__TweetDarkWithActionsStyle);
                } else {
                    ((BaseTweetView) rowView).setTweet(tweet);
                }
                return rowView;
            }


        }*/;


        lv.setAdapter(timelineAdapter);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                timelineAdapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast or some other action
                    }
                });
            }
        });



       /* for (Long longvar : tweetIds) {
            TweetUtils.loadTweet(longvar, new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    CompactTweetView compactTweetView = new CompactTweetView(EmbeddedTweets.this, result.data,R.style.tw__TweetDarkWithActionsStyle);
                    compactTweetView.setOnActionCallback(actionCallback);
                    myLayout.addView(compactTweetView);
                }

                @Override
                public void failure(TwitterException exception) {

                }
            });
        }*/
    }

    // launch the login activity when a guest user tries to favorite a Tweet
    final Callback<Tweet> actionCallback = new Callback<Tweet>() {
        @Override
        public void success(Result<Tweet> result) {
            // Intentionally blank
        }

        @Override
        public void failure(TwitterException exception) {
            if (exception instanceof TwitterAuthException) {
                // launch custom login flow
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        }
    };


    public void selectImage() {


        //intent for Filesystem.
        Intent _intent = new Intent();
        _intent.setType("image/*");
        _intent.setAction(Intent.ACTION_GET_CONTENT);

// Create AndroidExampleFolder at sdcard and // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        // Create camera captured image file path and name
        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        cameraImageUri = Uri.fromFile(sdImageMainDirectory);


// Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        // Camera capture image intent
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            cameraIntents.add(intent);
        }



// Create file chooser intent
        Intent c=Intent.createChooser(_intent, "Select Picture");
        // Set camera intent to file chooser
        c.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        // On select image call onActivityResult method of activity
        startActivityForResult(c,PICK_IMAGE_REQUEST);
    }
    private Uri cameraImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData) {

        if(aRequestCode==11){
            if(aResultCode==RESULT_OK){Toast.makeText(this,"Successfully Tweeted. ",Toast.LENGTH_LONG).show();}
            else{Toast.makeText(this,"Failure. ",Toast.LENGTH_LONG).show();}

        }


        else if(aRequestCode==PICK_IMAGE_REQUEST) {
            if (aResultCode == RESULT_OK) {
                try {
                    handleUserPickedImage(aData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        super.onActivityResult(aRequestCode, aResultCode, aData);

    }

    private void handleUserPickedImage(Intent aData) throws IOException {//


        final boolean isCamera;
        if (aData == null) {
            isCamera = true;
        } else {
            final String action = aData.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }

        Uri selectedImageUri;
        if (isCamera) {
            selectedImageUri = cameraImageUri;
        } else {
            selectedImageUri = (aData == null ? null : aData.getData());
        }
        uri=selectedImageUri;



        Intent i = new TweetComposer.Builder(EmbeddedTweets.this)
                .text("just setting up my Fabric.")
                .image(uri)
                .createIntent();

        startActivityForResult(i,11);




    }





}
