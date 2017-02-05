package com.pantiy.myevent.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.pantiy.myevent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Pantiy on 2016/10/22.
 */

public class TimePickerDialogFragment extends AppCompatDialogFragment {

    private static final String ARG_EVENT_TIME = "arg_eventTime";
    private static final String EXTRA_EVENT_TIME = "extra_eventTime";

    private static Date sDate;

    private TimePicker mTimePicker;

    public static TimePickerDialogFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_TIME,date);
        TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment();
        timePickerDialogFragment.setArguments(args);
        return timePickerDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_timepicker,null);

        Date date = (Date) getArguments().getSerializable(ARG_EVENT_TIME);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);

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

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK,sDate);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EVENT_TIME,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
