package com.pantiy.myevent.fragment;

import android.view.View;

import com.pantiy.myevent.model.Event;
import com.pantiy.myevent.model.EventLab;
import com.pantiy.myevent.R;

import java.util.List;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class UnsolvedEventListFragment extends EventListFragment {

    private static UnsolvedEventListFragment sUnsolvedEventListFragment;

    public static UnsolvedEventListFragment getUnsolvedEventListFragment() {
        if (sUnsolvedEventListFragment == null) {
            sUnsolvedEventListFragment = new UnsolvedEventListFragment();
        }
        return sUnsolvedEventListFragment;
    }

    @Override
    protected List<Event> getEvents() {
        return EventLab.getEventLab(getActivity()).getUnsolvedEvents();
    }

    @Override
    protected void setEventSolvedTextView() {
        mEventSolvedTextView.setText(R.string.unsolved);
    }

    @Override
    protected boolean getEventSolved() {
        return false;
    }

    @Override
    protected void checkEmptyEventList() {
        if (getEvents().size() == 0) {
            mEventList.setVisibility(View.GONE);
            mEmptySolvedEventList.setVisibility(View.GONE);
            mEmptyUnsolvedEventList.setVisibility(View.VISIBLE);
        } else {
            mEventList.setVisibility(View.VISIBLE);
            mEmptySolvedEventList.setVisibility(View.GONE);
            mEmptyUnsolvedEventList.setVisibility(View.GONE);
        }
    }

}
