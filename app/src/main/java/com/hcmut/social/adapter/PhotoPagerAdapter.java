package com.hcmut.social.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hcmut.social.R;
import com.hcmut.social.fragment.PickPhotoFragment;
import com.hcmut.social.fragment.TakePhotoFragment;

/**
 * Created by John on 10/18/2016.
 */

public class PhotoPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[];
    private Context context;

    public PhotoPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PickPhotoFragment.newInstance();
            case 1:
                return TakePhotoFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        tabTitles = new String[] {
                context.getString(R.string.library),
                context.getString(R.string.take_photo)
        };
        return tabTitles[position];
    }
}
