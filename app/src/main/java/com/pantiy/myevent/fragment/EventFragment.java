package com.pantiy.myevent.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.pantiy.myevent.model.Event;
import com.pantiy.myevent.model.EventLab;
import com.pantiy.myevent.R;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/10/19.
 */

public class EventFragment extends Fragment {

    private static final String ARG_EVENT_ID = "arg_eventId";
    private static final String EXTRA_EVENT_DATE = "extra_eventDate";
    private static final String EXTRA_EVENT_TIME = "extra_eventTime";
    private static final String DIALOG_DATE = "dialogDate";
    private static final String DIALOG_TIME = "dialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Event mEvent;
    private EditText mEventTitleEditText;
    private ImageButton mShareImageButton;
    private Button mEventDateButton;
    private Button mEventTimeButton;
    private CheckBox mEventIsSolvedCheckBox;
    private EditText mEventDetailEditText;

    public static EventFragment newInstance(UUID eventId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID,eventId);
        EventFragment eventFragment = new EventFragment();
        eventFragment.setArguments(args);
        return eventFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID eventId = (UUID) getArguments().getSerializable(ARG_EVENT_ID);
        mEvent = EventLab.getEventLab(getActivity()).getEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event,container,false);

        mEventTitleEditText = (EditText) view.findViewById(R.id.event_title_editText);
        mEventTitleEditText.setText(mEvent.getEventTitle());
        mEventTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setEventTitle(s.toString());
                updateEvent(mEvent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mShareImageButton = (ImageButton) view.findViewById(R.id.share_imageButton);
        mShareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_event_subject));
                intent.putExtra(Intent.EXTRA_TEXT,getShareMessage(mEvent));
                intent = Intent.createChooser(intent,getString(R.string.share_event_via));
                startActivity(intent);
            }
        });

        mEventDateButton = (Button) view.findViewById(R.id.event_date_button);
        updateDate();
        mEventDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerDialogFragment datePickerDialogFragment =
                        DatePickerDialogFragment.newInstance(mEvent.getEventDate());
                datePickerDialogFragment.setTargetFragment(EventFragment.this,REQUEST_DATE);
                datePickerDialogFragment.show(fragmentManager,DIALOG_DATE);
//                Intent intent = DatePickerActivity.newInstance(getActivity(),mEvent.getEventDate());
//                startActivityForResult(intent,REQUEST_DATE);
            }
        });

        mEventTimeButton = (Button) view.findViewById(R.id.event_time_button);
        updateTime();
        mEventTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerDialogFragment timePickerDialogFragment =
                        TimePickerDialogFragment.newInstance(mEvent.getEventDate());
                timePickerDialogFragment.setTargetFragment(EventFragment.this,REQUEST_TIME);
                timePickerDialogFragment.show(fragmentManager,DIALOG_TIME);
//                Intent intent = TimePickerActivity.newInstance(getActivity(),mEvent.getEventDate());
//                startActivityForResult(intent,REQUEST_TIME);
            }
        });

        mEventIsSolvedCheckBox = (CheckBox) view.findViewById(R.id.event_solved_checkBox);
        mEventIsSolvedCheckBox.setChecked(mEvent.isSolved());
        mEventIsSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEvent.setSolved(isChecked);
                updateEvent(mEvent);
            }
        });

        mEventDetailEditText = (EditText) view.findViewById(R.id.event_detail_editText);
        mEventDetailEditText.setText(mEvent.getEventDetail());
        mEventDetailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setEventDetail(s.toString());
                updateEvent(mEvent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_deleteEvent:
                EventLab.getEventLab(getActivity()).deleteEvent(mEvent);
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (data != null && requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(EXTRA_EVENT_DATE);
            mEvent.setEventDate(date);
            if (date == null) {
                return;
            }
            updateEvent(mEvent);
            updateDate();
        } else if (data != null && requestCode == REQUEST_TIME) {
            Date time = (Date) data.getSerializableExtra(EXTRA_EVENT_TIME);
            if (time == null) {
                return;
            }
            mEvent.setEventDate(time);
            updateEvent(mEvent);
            updateTime();
        }
    }

    private String getShareMessage(Event event) {
        String shareMessage;
        String dateFormat = "yyyy-MM-dd(EEE), kk:mm";
        String eventDate = "时间：\n" + DateFormat.format(dateFormat,event.getEventDate()).toString();
        shareMessage = event.getEventTitle() + "\n\n" + eventDate + "\n\n" + event.getEventDetail();
        return shareMessage;
    }

    private void updateDate() {
        String dateFormat = "yyyy-MM-dd , EEE";
        mEventDateButton.setText(DateFormat.format(dateFormat,mEvent.getEventDate()));
    }

    private void updateTime() {
        String timeFormat = "kk:mm";
        mEventTimeButton.setText(DateFormat.format(timeFormat,mEvent.getEventDate()));
    }

    private void updateEvent(Event event) {
        EventLab.getEventLab(getActivity()).updateEvent(event);
    }
}