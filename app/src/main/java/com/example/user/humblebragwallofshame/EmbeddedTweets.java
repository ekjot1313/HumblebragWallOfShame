package com.example.user.humblebragwallofshame;
// EmbeddedTweetsActivity
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;


import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;



import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;

public class EmbeddedTweets extends AppCompatActivity {
Uri uri;
     static TweetTimelineListAdapter[] adapter = new TweetTimelineListAdapter[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_tweets);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));


        FloatingActionButton ct=(FloatingActionButton)findViewById(R.id.composetweet);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 30);
        myAnim.setInterpolator(interpolator);

        ct.startAnimation(myAnim);



         final ListView lv=(ListView)findViewById(R.id.lv);

        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

         final TweetAdapter mTweetAdapter;





        ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               selectImage();


            }
        });








        final FixedTweetTimeline[] timeline = new FixedTweetTimeline[1];

        TwitterApiClient twitterApiClient = Twitter.getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

        Call<List<Tweet>> call =  statusesService.userTimeline(null,"@humblebrag",102, null, null, null, null, null, null);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                ListView listView = (ListView) findViewById(R.id.lv);
                timeline[0] = new FixedTweetTimeline.Builder()
                        .setTweets(result.data)
                        .build();
                 adapter[0] = new TweetAdapter(EmbeddedTweets.this,timeline[0]);

                lv.setAdapter(adapter[0]);


            }

            @Override
            public void failure(TwitterException e) {
            }
        });




        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                adapter[0].refresh(new Callback<TimelineResult<Tweet>>() {
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
                .text("Write Here.")
                .image(uri)
                .createIntent();

        startActivityForResult(i,11);




    }


    static TweetTimelineListAdapter getAdapter(){

    return  adapter[0];
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_logout:

                new Logout(EmbeddedTweets.this).logout();
                break;

        }

        return true;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

class MyBounceInterpolator implements android.view.animation.Interpolator {
    double mAmplitude = 1;
    double mFrequency = 10;

    MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}