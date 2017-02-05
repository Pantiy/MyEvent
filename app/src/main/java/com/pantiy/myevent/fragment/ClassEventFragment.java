package com.pantiy.myevent.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pantiy.myevent.R;

/**
 * Created by Pantiy on 2016/11/16.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public class ClassEventFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_class_event,container,false);

        return view;
    }
}
