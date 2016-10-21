package com.hcmut.social.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hcmut.social.R;
import com.hcmut.social.adapter.PagerAdapter;


public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*Set Tab view custom*/
        customViewTabLayout(R.layout.view_home_tab, 0);
        customViewTabLayout(R.layout.view_search_tab, 1);
        customViewTabLayout(R.layout.view_favorites_tab, 2);
        customViewTabLayout(R.layout.view_mypage_tab, 3);

    }

    private void customViewTabLayout(int resource, int index) {
        View v = View.inflate(this.getBaseContext(),resource, null);
        if (v == null) {
            throw new RuntimeException("View is null");
        }
        tabLayout.getTabAt(index).setCustomView(v);
    }

    @Override
    protected int[] getListEventHandle() {
        return new int[0];
    }
}
