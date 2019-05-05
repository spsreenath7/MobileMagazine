package com.wit.magazine.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wit.magazine.R;
import com.wit.magazine.adapters.PageAdapter;


public class ArticlesFragment extends Fragment {

    private View v;
    private ViewPager viewPager;
    private PageAdapter adapter;
    private TabLayout tabLayout;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    public static ArticlesFragment newInstance() {
        ArticlesFragment fragment = new ArticlesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_articles, container, false);
        viewPager = v.findViewById(R.id.viewPager);
        updateView();
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        getActivity().setTitle("Welcome");



        return v;
    }

    public void onResume() {
        super.onResume();
//        getAllCoffees();
//        updateView();
    }

    private void updateView() {
        adapter = new PageAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    // TODO: Rename method, update argument and hook method into UI event




}
