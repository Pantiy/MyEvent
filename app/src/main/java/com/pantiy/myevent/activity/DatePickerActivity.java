package com.pantiy.myevent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.pantiy.myevent.fragment.DatePickerFragment;

import java.util.Date;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class DatePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_EVENT_DATE = "extra_eventDate";

    public static Intent newInstance(Context packageContext, Date date) {
        Intent intent = new Intent(packageContext,DatePickerActivity.class);
        intent.putExtra(EXTRA_EVENT_DATE,date);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_EVENT_DATE);
        return DatePickerFragment.newInstance(date);
    }
}
