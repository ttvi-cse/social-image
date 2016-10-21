package com.hcmut.social.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcmut.social.R;
import com.hcmut.social.fragment.FavoriteFragment;
import com.hcmut.social.fragment.HomePageFragment;
import com.hcmut.social.fragment.MyPageFragment;
import com.hcmut.social.fragment.SearchFragment;

/**
 * Created by John on 10/13/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[];
    private Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomePageFragment.newInstance();
            case 1:
                return SearchFragment.newInstance();
            case 2:
                return FavoriteFragment.newInstance();
            case 3:
                return MyPageFragment.newInstance();
            default:
                break;
        }

        return HomePageFragment.newInstance();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        tabTitles = new String[] {
                context.getString(R.string.home_page),
                context.getString(R.string.search),
                context.getString(R.string.favorite),
                context.getString(R.string.my_page),
        };
        return tabTitles[position];
    }
}
