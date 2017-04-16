package com.example.user.humblebragwallofshame;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class UserFragment extends Fragment {
    View view;
    TextView usernameTV,twitterHandleTV,bioTV,locationTV,followersCountTV,followingCountTV,tweetCountTV;
    ImageView profilePicIV,coverPicIV;
    TweetTimelineListAdapter adapter=EmbeddedTweets.getAdapter();;



int position;

   void setPosition(int position){

this.position=position;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_user_fragment, container, false);

        User user;



        if(adapter.getItem(position).retweetedStatus!=null){
            user=adapter.getItem(position).retweetedStatus.user;
        }
        else{user=adapter.getItem(position).user;}













        String username=(user.name),
                twitter_handle="@"+user.screenName,
                bio=user.description,
                location=user.location;
        int
                followers_count=user.followersCount,
                following_count=user.friendsCount,
                tweet_count=user.statusesCount;

        String profile_image_url=user.profileImageUrlHttps.replace("_normal", ""),
                cover_image_url=user.profileBackgroundImageUrl;

        usernameTV=(TextView)view.findViewById(R.id.usernametv);
        twitterHandleTV=(TextView)view.findViewById(R.id.handletv);
        bioTV=(TextView)view.findViewById(R.id.biotv);
        locationTV=(TextView)view.findViewById(R.id.locationtv);
        followersCountTV=(TextView)view.findViewById(R.id.followerscounttv);
        followingCountTV=(TextView)view.findViewById(R.id.followingcounttv);
        tweetCountTV=(TextView)view.findViewById(R.id.tweetcounttv);


        usernameTV.setText(""+username);
        twitterHandleTV.setText(""+twitter_handle);
        bioTV.setText(""+bio);
        locationTV.setText(""+location);
        followersCountTV.setText(""+followers_count);
        followingCountTV.setText(""+following_count);
        tweetCountTV.setText(""+tweet_count);


        profilePicIV=(ImageView)view.findViewById(R.id.profilepiciv);
        coverPicIV=(ImageView)view.findViewById(R.id.coverpiciv);

        Picasso.with(view.getContext())
                .load(profile_image_url)
                .error(R.drawable.ic_action_name)
                .fit()
                .transform(new RoundedCornersTransform(1f))
                .into(profilePicIV);

        Picasso.with(view.getContext())
                .load(cover_image_url)
                .error(R.drawable.default_cover_img)
                .fit()
                .into(coverPicIV);




        return view;
    }
}
