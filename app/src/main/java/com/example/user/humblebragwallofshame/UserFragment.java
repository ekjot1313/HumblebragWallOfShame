package com.example.user.humblebragwallofshame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class UserFragment extends Fragment {
    View view;
    TextView usernameTV,twitterHandleTV,bioTV,locationTV,followersCountTV,followingCountTV,tweetCountTV,img1,img2;
    ImageView imgview,imgview2;
    TweetTimelineListAdapter adapter=EmbeddedTweets.getAdapter();;



int position;

   void setPosition(int position){

this.position=position;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_user_fragment, container, false);

        User user;
        //Toast.makeText(getContext(),adapter.getItem(position).source,Toast.LENGTH_LONG).show();




  /*
if(!adapter.getItem(position).retweeted){user=adapter.getItem(position).retweetedStatus.user;
    Toast.makeText(getContext(),"retweeted",Toast.LENGTH_LONG);}
         else*/
        user=adapter.getItem(position).retweetedStatus.user;











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
        img1=(TextView)view.findViewById(R.id.img1tv);
        img2=(TextView)view.findViewById(R.id.img2tv);

        usernameTV.setText(""+username);
        twitterHandleTV.setText(""+twitter_handle);
        bioTV.setText(""+bio);
        locationTV.setText(""+location);
        followersCountTV.setText(""+followers_count);
        followingCountTV.setText(""+following_count);
        tweetCountTV.setText(""+tweet_count);
        img1.setText(""+profile_image_url);
        img2.setText(""+cover_image_url);

        imgview=(ImageView)view.findViewById(R.id.imgview);
        imgview2=(ImageView)view.findViewById(R.id.imgview2);

        Picasso.with(view.getContext())
                .load(profile_image_url)
                .placeholder(R.drawable.tw__ic_logo_default) // optional
                .error(R.drawable.tw__ic_logo_default)
                .resize(900, 600)
                .into(imgview);

        Picasso.with(view.getContext())
                .load(cover_image_url)
                .placeholder(R.drawable.tw__ic_logo_default) // optional
                .error(R.drawable.tw__ic_logo_default)
                .resize(900, 600)
                .into(imgview2);




        return view;
    }
}
