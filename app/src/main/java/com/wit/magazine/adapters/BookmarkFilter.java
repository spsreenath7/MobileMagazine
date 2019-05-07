package com.wit.magazine.adapters;

import android.util.Log;
import android.widget.Filter;

import com.wit.magazine.models.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFilter extends Filter {
    public List<Bookmark> bookmarkFullList;
    public String filterText;
    public BookmarkListAdpater adapter;

    public BookmarkFilter(List<Bookmark> bookmarkFullList, String filterText,
                        BookmarkListAdpater adapter) {
        super();
        this.bookmarkFullList = bookmarkFullList;
        this.filterText = filterText;
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        this.filterText = filterText;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence prefix) {
        Filter.FilterResults results = new Filter.FilterResults();

        List<Bookmark> bookmarkListFiltered;
        String bookmarkTitle;

//        if (prefix == null || prefix.length() == 0) {
//            bookmarkListFiltered = new ArrayList<>();
//            if (filterText.equals("all")) {
//                results.values = bookmarkList;
//                results.count = bookmarkList.size();
//            }
//        }else {
                String prefixString = prefix.toString().toLowerCase();
                bookmarkListFiltered = new ArrayList<>();


                for (Bookmark bookmark : bookmarkFullList) {
                    bookmarkTitle = bookmark.getTitle().toLowerCase();
                    if (bookmarkTitle.contains(prefixString)) {
                        if (filterText.equals("all")) {
                            bookmarkListFiltered.add(bookmark);
                        }
                    }
                }
                results.values = bookmarkListFiltered;
                results.count = bookmarkListFiltered.size();
//            }
            return results;
        }


    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {

        adapter.bookmarkList.clear();
        adapter.bookmarkList = (ArrayList<Bookmark>) results.values;

        if (results.count >= 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.bookmarkList = this.bookmarkFullList;
        }
    }
}

