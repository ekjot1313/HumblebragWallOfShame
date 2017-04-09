package com.example.user.humblebragwallofshame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

/**
 * Created by user on 10-Apr-17.
 */

class TweetAdapter extends TweetTimelineListAdapter {

    public TweetAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view = convertView;
        final Tweet tweet = getItem(position);
        if (view == null) {
            view = new CompactTweetView(context, tweet, R.style.tw__TweetDarkStyle);
        } else {
            ((BaseTweetView) view).setTweet(tweet);
        }
        //disable subviews to avoid links are clickable
        if(view instanceof ViewGroup){
            disableViewAndSubViews((ViewGroup) view);
        }

        //enable root view and attach custom listener
        view.setEnabled(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetId = "click tweetId:"+getItemId(position);
                Toast.makeText(context, tweetId, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    //helper method to disable subviews

    private void disableViewAndSubViews(ViewGroup layout)
    { layout.setEnabled(false);

        for (int i = 0; i < (layout.getChildCount()); i++)
        {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup)
            { disableViewAndSubViews((ViewGroup) child);
            }
            else
                { child.setEnabled(false);
                    child.setClickable(false);
                    child.setLongClickable(false);
                }

        }
    }

}