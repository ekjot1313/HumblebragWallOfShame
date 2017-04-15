package com.example.user.humblebragwallofshame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.twitter.sdk.android.core.models.User;

/**
 * Created by user on 15-Apr-17.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
        public static int LOOPS_COUNT = 100;


        public MyPagerAdapter(FragmentManager manager) {
                super(manager);

        }


        @Override
        public Fragment getItem(int position) {
                UserFragment uf = new UserFragment();
                uf.setPosition(position);

                return uf;
        }

        @Override
        public int getCount() {
                return 100;
        }
}