package com.pantiy.myevent.fragment;

import android.view.View;
import com.pantiy.myevent.model.Event;
import com.pantiy.myevent.model.EventLab;
import com.pantiy.myevent.R;
import java.util.List;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class SolvedEventListFragment extends EventListFragment {

    private static SolvedEventListFragment sSolvedEventListFragment;

    public static SolvedEventListFragment getSolvedEventListFragment() {
        if (sSolvedEventListFragment == null) {
            sSolvedEventListFragment = new SolvedEventListFragment();
        }
        return sSolvedEventListFragment;
    }

    @Override
    protected List<Event> getEvents() {
        return EventLab.getEventLab(getActivity()).getSolvedEvents();
    }

    @Override
    protected void setEventSolvedTextView() {
        mEventSolvedTextView.setText(R.string.solved);
    }

    @Override
    protected boolean getEventSolved() {
        return true;
    }

    @Override
    protected void checkEmptyEventList() {
        if (getEvents().size() == 0) {
            mEventList.setVisibility(View.GONE);
            mEmptySolvedEventList.setVisibility(View.VISIBLE);
            mEmptyUnsolvedEventList.setVisibility(View.GONE);
        } else {
            mEventList.setVisibility(View.VISIBLE);
            mEmptySolvedEventList.setVisibility(View.GONE);
            mEmptyUnsolvedEventList.setVisibility(View.GONE);
        }
    }
}
