package com.example.user.humblebragwallofshame;

import android.content.Context;
import android.content.Intent;

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
static int position;
    public TweetAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position=position;
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
                //String tweetId = "click tweetId:"+getItem(position).retweetedStatus.user.idStr;
                //Toast.makeText(context, position+"", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,UserInfo.class);
                intent.putExtra("position",position);
                context.startActivity(intent);


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



            if (child instanceof ViewGroup&&!child.toString().equals(""))
            { disableViewAndSubViews((ViewGroup) child);
            }
            else
                { child.setEnabled(false);
                    child.setClickable(false);
                    child.setLongClickable(false);
                }

        }
    }


    int getPosition(){return position;}
    void setPosition(int position){this.position=position;}


}





/*
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolderTweet> {

    private LayoutInflater layoutInflater;
    private List<Tweet> mListTweet;

    public TweetAdapter(Context context) {

        layoutInflater = LayoutInflater.from(context);
    }

    public TweetAdapter() {
    }

    @Override
    public ViewHolderTweet onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.tweet_layout, parent, false);
        ViewHolderTweet viewHolderTweet = new ViewHolderTweet(view);
        return viewHolderTweet;
    }

    public void setTweets(List<Tweet> mListTweet) {
        this.mListTweet = mListTweet;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolderTweet holder, int position) {
        Tweet currentTweet = mListTweet.get(position);

        //Set user details
        User user = currentTweet.user;
        holder.mUserName.setText(user.name);
        holder.mUserHandle.setText(user.screenName);
        // loadImages(user.profileImageUrl, holder);

        //retrieved date may be null
//        String createdAt = currentTweet.createdAt;

        holder.mRetweetCount.setText(currentTweet.retweetCount);
        holder.mFavCount.setText(currentTweet.favoriteCount);
        holder.mTweet.setText(currentTweet.text);

//        mPreviousPosition = position;


    }


    @Override
    public int getItemCount() {

        return mListTweet.size();
    }

    static class ViewHolderTweet extends RecyclerView.ViewHolder {

        private ImageView mProfilePic;
        private TextView mTweet;
        private TextView mRetweetCount;
        private TextView mFavCount;
        private TextView mUserName;
        private TextView mUserHandle;


        public ViewHolderTweet(View itemView) {
            super(itemView);

            mProfilePic = (ImageView) itemView.findViewById(R.id.iv_user_image);
            mTweet = (TextView) itemView.findViewById(R.id.tv_user_tweet);
            mRetweetCount = (TextView) itemView.findViewById(R.id.tv_retweet_count);
            mFavCount = (TextView) itemView.findViewById(R.id.tv_fav_count);
            mUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mUserHandle = (TextView) itemView.findViewById(R.id.tv_user_handle);


        }
    }

}

*/