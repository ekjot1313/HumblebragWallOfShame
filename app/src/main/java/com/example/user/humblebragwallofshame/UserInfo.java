package com.example.user.humblebragwallofshame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.List;

import retrofit2.Call;

public class UserInfo extends AppCompatActivity {

    TextView usernameTV,twitterHandleTV,bioTV,locationTV,followersCountTV,followingCountTV,tweetCountTV,img1,img2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        int position=getIntent().getExtras().getInt("position");



        TweetTimelineListAdapter adapter=EmbeddedTweets.getAdapter();




        User user=adapter.getItem(position).retweetedStatus.user;











        String username=(user.name),
                twitter_handle="@"+user.screenName,
                bio=user.description,
                location=user.location;
        int
                followers_count=user.followersCount,
                following_count=user.friendsCount,
                tweet_count=user.statusesCount;

        String profile_image_url=user.profileImageUrl,
                cover_image_url=user.profileBackgroundImageUrl;

        usernameTV=(TextView)findViewById(R.id.usernametv);
        twitterHandleTV=(TextView)findViewById(R.id.handletv);
        bioTV=(TextView)findViewById(R.id.biotv);
        locationTV=(TextView)findViewById(R.id.locationtv);
        followersCountTV=(TextView)findViewById(R.id.followerscounttv);
        followingCountTV=(TextView)findViewById(R.id.followingcounttv);
        tweetCountTV=(TextView)findViewById(R.id.tweetcounttv);
        img1=(TextView)findViewById(R.id.img1tv);
        img2=(TextView)findViewById(R.id.img2tv);

        usernameTV.setText(""+username);
        twitterHandleTV.setText(""+twitter_handle);
        bioTV.setText(""+bio);
        locationTV.setText(""+location);
        followersCountTV.setText(""+followers_count);
        followingCountTV.setText(""+following_count);
        tweetCountTV.setText(""+tweet_count);
        img1.setText(""+profile_image_url);
        img2.setText(""+cover_image_url);








    }
}
