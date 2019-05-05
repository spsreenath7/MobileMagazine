package com.wit.magazine.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//import ie.cm.fragments.ActsFragment;
//import ie.cm.fragments.CoffeeFragment;
//import ie.cm.fragments.DataFragment;
//import ie.cm.fragments.TabtestFragment;
import com.wit.magazine.R;
import com.wit.magazine.fragments.GeneralFragment;
import com.wit.magazine.fragments.MostreadFragment;
import com.wit.magazine.fragments.TrendingFragement;

public class PageAdapter extends FragmentPagerAdapter {
    FragmentManager fm;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {

        position = position+1;

        TrendingFragement trendingFragement= (TrendingFragement)
                fm.findFragmentById(R.id.recommLayout);
        MostreadFragment mostreadFragment = (MostreadFragment)
                fm.findFragmentById(R.id.mostreadLayout);
        GeneralFragment generalFragment = (GeneralFragment)
                fm.findFragmentById(R.id.generalLayout);

        switch (position) {
            case 1:
                return trendingFragement == null ? TrendingFragement.newInstance() : trendingFragement;
            case 2:
                return mostreadFragment == null ? MostreadFragment.newInstance() : mostreadFragment;
            case 3:
                return generalFragment == null ? GeneralFragment.newInstance() : generalFragment;
        }

        return GeneralFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        position = position+1;

        switch (position) {
            case 1:
                return "Trending";
            case 2:
                return "Most Read";
            case 3:
                return "General";
        }

        return "Trending";
    }
}
