package com.wit.magazine.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wit.magazine.R;

public class TabtestFragment extends Fragment {

    public TabtestFragment() {
        // Required empty public constructor
    }

    public static TabtestFragment newInstance() {
        TabtestFragment fragment = new TabtestFragment();

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragement_testtab, container, false);
        getActivity().setTitle("Personal Data");



        return v;
    }
}
