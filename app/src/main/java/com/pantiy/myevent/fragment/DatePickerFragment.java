package com.pantiy.myevent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.pantiy.myevent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class DatePickerFragment extends Fragment {

    private static final String ARG_EVENT_DATE = "arg_eventDate";
    private static final String EXTRA_EVENT_DATE = "extra_eventDate";

    private DatePicker mDatePicker;
    private Button mConfirmButton;
    private Button mCancelButton;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_DATE,date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_datepicker,container,false);

        Date date = (Date) getArguments().getSerializable(ARG_EVENT_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(year,month,day,null);

        mConfirmButton = (Button) view.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year,month,day,hour,minute).getTime();
                sendResult(Activity.RESULT_OK,date);
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
        intent.putExtra(EXTRA_EVENT_DATE,date);
        getActivity().setResult(resultCode,intent);
    }
}
