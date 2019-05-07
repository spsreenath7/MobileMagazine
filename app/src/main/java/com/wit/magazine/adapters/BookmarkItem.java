package com.wit.magazine.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import com.wit.magazine.R;
import com.wit.magazine.models.Bookmark;


public class BookmarkItem {

    public View view;

    public BookmarkItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Bookmark bookmark)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bookmarkcard, parent, false);

        view.setTag(bookmark.getBookmarkId());
//        view.setTag(1,bookmark.getSource());
//        view.setTag(2,"10");
//        view.setTag(3,bookmark.getUrl());

        ((TextView) view.findViewById(R.id.textViewBTitle)).setText(bookmark.getTitle());
        ((TextView) view.findViewById(R.id.textViewSource)).setText(bookmark.getSource());
        ((TextView) view.findViewById(R.id.textViewTagtext)).setText(bookmark.getTag());
        ((TextView) view.findViewById(R.id.textViewCatogery)).setText(bookmark.getCatogery());

        ImageView imgDelete = view.findViewById(R.id.imageViewDeleteBookmark);
        imgDelete.setTag(bookmark);
        imgDelete.setOnClickListener(deleteListener);
    }

}
