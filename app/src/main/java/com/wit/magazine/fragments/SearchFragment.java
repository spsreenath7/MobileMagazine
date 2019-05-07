package com.wit.magazine.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.wit.magazine.R;
import com.wit.magazine.adapters.BookmarkFilter;

public class SearchFragment extends BookmarkFragment implements AdapterView.OnItemSelectedListener{

    String selected;
    SearchView searchView;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle(R.string.searchBookmarkLbl);
        listView = v.findViewById(R.id.searchList); //Bind to the list on our Search layout
//        setListView(v);
        updateView();
        listView.setOnItemClickListener(this);
//        setSwipeRefresh(v);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.bookmarkCatogery,
                        android.R.layout.simple_spinner_item);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.searchSpinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.setQueryHint("Search bookmarks");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("magazine","Search text in onQueryTextSubmit : "+query);
                bookmarkFilter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.v("magazine","Search text in onQueryTextChange : "+newText);
                bookmarkFilter.filter(newText);
                return false;
            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
//        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void checkSelected(String selected)
    {
        if (selected != null) {
            if (selected.equals("All Types")) {
                bookmarkFilter.setFilter("all");
            } else  {
                bookmarkFilter.setFilter(selected);
            }

            String filterText = ((SearchView)activity
                    .findViewById(R.id.searchView)).getQuery().toString();

            if(filterText.length() > 0)
                bookmarkFilter.filter(filterText);
            else
                bookmarkFilter.filter("");
        }
    }
}
