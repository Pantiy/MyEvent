package com.pantiy.myevent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.pantiy.myevent.model.Event;
import com.pantiy.myevent.model.EventLab;
import com.pantiy.myevent.fragment.EventFragment;
import com.pantiy.myevent.R;

import java.util.List;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/10/19.
 */

public class EventPagerActivity extends AppCompatActivity {

    private static final String EXTRA_EVENT_ID = "extra_eventId";
    private static final String EXTRA_EVENT_SOLVED = "extra_eventSolved";

    private List<Event> mEvents;
    private ViewPager mViewPager;

    private static UUID mEventId;
    private static boolean eventSolved;

    public static Intent newInstance(Context packageContext, UUID eventId, boolean eventSolved) {
        Intent intent = new Intent(packageContext,EventPagerActivity.class);
        intent.putExtra(EXTRA_EVENT_ID,eventId);
        intent.putExtra(EXTRA_EVENT_SOLVED,eventSolved);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_event);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mEventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        eventSolved = getIntent().getBooleanExtra(EXTRA_EVENT_SOLVED,false);

        if (eventSolved) {
            mEvents = EventLab.getEventLab(this).getSolvedEvents();
        } else {
            mEvents = EventLab.getEventLab(this).getUnsolvedEvents();
        }

        mViewPager = (ViewPager) findViewById(R.id.event_viewPager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Event event = mEvents.get(position);
                return EventFragment.newInstance(event.getEventId());
            }

            @Override
            public int getCount() {
                return mEvents.size();
            }
        });

        for (int i = 0;i < mEvents.size();i++) {
            if (mEvents.get(i).getEventId().equals(mEventId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
