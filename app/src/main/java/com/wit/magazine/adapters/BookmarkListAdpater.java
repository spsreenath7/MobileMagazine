package com.wit.magazine.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import com.wit.magazine.R;
import com.wit.magazine.models.Bookmark;

public class BookmarkListAdpater extends ArrayAdapter<Bookmark> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<Bookmark> bookmarkList;
    private List<Bookmark> bookmarkFullList;

    public BookmarkListAdpater(Context context, View.OnClickListener deleteListener, List<Bookmark> bookmarkList)
    {
        super(context, R.layout.bookmarkcard, bookmarkList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.bookmarkList = bookmarkList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookmarkItem item = new BookmarkItem(context, parent,
                deleteListener, bookmarkList.get(position));

        return item.view;
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public Bookmark getItem(int position) {
        return bookmarkList.get(position);
    }


}
