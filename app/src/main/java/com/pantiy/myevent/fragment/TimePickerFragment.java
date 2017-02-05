package com.pantiy.myevent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.pantiy.myevent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class TimePickerFragment extends Fragment {

    private static final String ARG_EVENT_TIME = "arg_eventTime";
    private static final String EXTRA_EVENT_TIME = "extra_eventTime";

    private static Date sDate;

    private TimePicker mTimePicker;
    private Button mConfirmButton;
    private Button mCancelButton;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_TIME,date);
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(args);
        return timePickerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timepicker,container,false);

        Date date = (Date) getArguments().getSerializable(ARG_EVENT_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT > 22) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                sDate = new GregorianCalendar(year,month,day,hourOfDay,minute).getTime();
            }
        });

        mConfirmButton = (Button) view.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(Activity.RESULT_OK,sDate);
                getActivity().finish();
            }
        });

        mCancelButton = (Button) view.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void sendResult(int resultCode, Date date) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EVENT_TIME,date);
        getActivity().setResult(resultCode,intent);
    }
}
