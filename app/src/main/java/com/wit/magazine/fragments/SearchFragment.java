package com.wit.magazine.fragments;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wit.magazine.R;
import com.wit.magazine.adapters.BookmarkFilter;
import com.wit.magazine.adapters.BookmarkListAdpater;
import com.wit.magazine.models.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BookmarkFragment implements AdapterView.OnItemSelectedListener{

    String selected;
    SearchView searchView;
    Query catogeryQuery;


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
                List<Bookmark> bookmarkListFiltered;
                String bookmarkTitle;

                String prefixString = query.toString().toLowerCase();
                bookmarkListFiltered = new ArrayList<>();
                for (Bookmark bookmark : bookmarkList) {
                    bookmarkTitle = bookmark.getTitle().toLowerCase();
                    if (bookmarkTitle.contains(prefixString)) {
                            bookmarkListFiltered.add(bookmark);

                    }
                }
                BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, SearchFragment.this, bookmarkListFiltered);
                listView.setAdapter(trackListAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)  {
                Log.v("magazine","Search text in onQueryTextSubmit : "+newText);
                List<Bookmark> bookmarkListFiltered;
                String bookmarkTitle;

                String prefixString = newText.toString().toLowerCase();
                bookmarkListFiltered = new ArrayList<>();
                for (Bookmark bookmark : bookmarkList) {
                    bookmarkTitle = bookmark.getTitle().toLowerCase();
                    if (bookmarkTitle.contains(prefixString)) {
                        bookmarkListFiltered.add(bookmark);

                    }
                }
                BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, SearchFragment.this, bookmarkListFiltered);
                listView.setAdapter(trackListAdapter);
                return false;
            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        Log.v("magazine", "Search text in selectedChange : " + selected);
        if (!"None".equals(selected)) {
            List<Bookmark> filteredBookMarkList = new ArrayList<>();
            for (Bookmark bookmark : bookmarkList) {
                if (selected.equals(bookmark.getCatogery())) {
                    filteredBookMarkList.add(bookmark);
                }
            }
            BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, SearchFragment.this, filteredBookMarkList);
            listView.setAdapter(trackListAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
