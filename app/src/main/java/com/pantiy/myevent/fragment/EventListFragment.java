package com.pantiy.myevent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pantiy.myevent.model.Event;
import com.pantiy.myevent.model.EventLab;
import com.pantiy.myevent.activity.EventListPagerActivity;
import com.pantiy.myevent.activity.EventPagerActivity;
import com.pantiy.myevent.R;

import java.util.List;

/**
 * Created by Pantiy on 2016/10/19.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public abstract class EventListFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected EventAdapter mEventAdapter;
    protected TextView mEventSolvedTextView;
    protected LinearLayout mEventList;
    protected LinearLayout mEmptyUnsolvedEventList;
    protected LinearLayout mEmptySolvedEventList;
    protected ImageButton mAddEventImageButton;

    protected List<Event> mEvents;

    protected abstract List<Event> getEvents();
    protected abstract void setEventSolvedTextView();
    protected abstract boolean getEventSolved();
    protected abstract void checkEmptyEventList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_event,container,false);

        mEventSolvedTextView = (TextView) view.findViewById(R.id.isSolved_textView);
        setEventSolvedTextView();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEventList = (LinearLayout) view.findViewById(R.id.event_list);
        mEmptyUnsolvedEventList = (LinearLayout) view.findViewById(R.id.empty_unsolved_event_list);
        mEmptySolvedEventList = (LinearLayout) view.findViewById(R.id.empty_solved_event_list);

        mAddEventImageButton = (ImageButton) view.findViewById(R.id.addEvent_imageButton);
        mAddEventImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

        checkEmptyEventList();

        updateEventList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateEventList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_event,menu);
        MenuItem showSubtitleItem = menu.findItem(R.id.menu_item_showSubtitle);
        if (EventListPagerActivity.mSubtitleVisible) {
            showSubtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            showSubtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_addEvent:
                addEvent();
                return true;

            case R.id.menu_item_showSubtitle:
                EventListPagerActivity.mSubtitleVisible = !EventListPagerActivity.mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addEvent() {
        Event event = new Event();
        EventLab.getEventLab(getActivity()).addEvent(event);
        updateEventList();
        Intent intent = EventPagerActivity
                .newInstance(getActivity(),event.getEventId(),false);
        startActivity(intent);
    }

    public void updateEventList() {

        mEvents = getEvents();

        if (mEventAdapter == null) {
            mEventAdapter = new EventAdapter(mEvents);
            mRecyclerView.setAdapter(mEventAdapter);
        } else {
            mEventAdapter.setEvents(mEvents);
            mEventAdapter.notifyDataSetChanged();
        }

        checkEmptyEventList();
        updateSubtitle();
    }

    private void updateSubtitle() {

        int unsolvedEventsNum = EventLab.getEventLab(getActivity()).getUnsolvedEvents().size();
        String subtitle = getString(R.string.subtitle,unsolvedEventsNum);
        if (!EventListPagerActivity.mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    protected class EventHolder extends RecyclerView.ViewHolder {

        private Event mEvent;
        private TextView mEventTitle;
        private TextView mEventDate;
        private CheckBox mEventIsSolved;
        private int mPosition;

        private EventHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = EventPagerActivity
                            .newInstance(getActivity(),mEvent.getEventId(), getEventSolved());
                    startActivity(intent);
                }
            });

            mEventTitle = (TextView) itemView.findViewById(R.id.list_item_eventTitle);
            mEventDate = (TextView) itemView.findViewById(R.id.list_item_eventDate);
            mEventIsSolved = (CheckBox) itemView.findViewById(R.id.list_item_event_checkBox);
            mEventIsSolved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvent.setSolved(!mEvent.isSolved());
                    EventLab.getEventLab(getActivity()).updateEvent(mEvent);
                    mEvents.remove(mEvent);
                    mEventAdapter.notifyItemRemoved(mPosition);
                    mEventAdapter.notifyItemRangeChanged(mPosition,mEvents.size());
                    checkEmptyEventList();
                    if (!getEventSolved()) {
                        SolvedEventListFragment.getSolvedEventListFragment().updateEventList();
                    } else {
                        UnsolvedEventListFragment.getUnsolvedEventListFragment().updateEventList();
                    }
//                    UnsolvedEventListFragment.getUnsolvedEventListFragment().updateEventList();
//                    SolvedEventListFragment.getSolvedEventListFragment().updateEventList();
                }
            });

        }

        private void bindEvent(Event event, int position) {
            String dateFormat = "yyyy-MM-dd EEE , kk:mm";
            mEvent = event;
            mPosition = position;
            mEventTitle.setText(mEvent.getEventTitle());
            mEventDate.setText(DateFormat.format(dateFormat,mEvent.getEventDate()));
            mEventIsSolved.setChecked(mEvent.isSolved());
        }
    }

    protected class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<Event> mEvents;

        private EventAdapter(List<Event> events) {
            mEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_event,parent,false);
            return new EventHolder(view);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            holder.bindEvent(mEvents.get(position),position);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        private void setEvents(List<Event> events) {
            mEvents = events;
        }
    }

}
