package com.pantiy.myevent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.pantiy.myevent.fragment.TimePickerFragment;

import java.util.Date;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class TimePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_EVENT_TIME = "extra_eventTime";

    public static Intent newInstance(Context packageContext, Date date) {
        Intent intent = new Intent(packageContext,TimePickerActivity.class);
        intent.putExtra(EXTRA_EVENT_TIME,date);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_EVENT_TIME);
        return TimePickerFragment.newInstance(date);
    }
}
