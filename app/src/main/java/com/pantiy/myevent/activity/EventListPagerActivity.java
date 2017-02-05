package com.pantiy.myevent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.pantiy.myevent.fragment.SolvedEventListFragment;
import com.pantiy.myevent.fragment.UnsolvedEventListFragment;
import com.pantiy.myevent.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class EventListPagerActivity extends AppCompatActivity {

    private static final String SAVED_SUBTITLE_VISIBLE = "saved_subtitleVisible";

    public static boolean mSubtitleVisible;

    private ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_event);

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE, false);
        }

        mFragments.add(UnsolvedEventListFragment.getUnsolvedEventListFragment());
        mFragments.add(SolvedEventListFragment.getSolvedEventListFragment());

        mViewPager = (ViewPager) findViewById(R.id.event_viewPager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}