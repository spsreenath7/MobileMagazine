package com.wit.magazine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.adapters.BookmarkFilter;
import com.wit.magazine.adapters.BookmarkListAdpater;
import com.wit.magazine.models.Bookmark;

import static com.wit.magazine.activities.HomeActivity.app;

public class BookmarkFragment extends Fragment implements AdapterView.OnItemClickListener,  View.OnClickListener, AbsListView.MultiChoiceModeListener{

    HomeActivity activity;
    public View v;
    public ListView listView;

    public List<Bookmark> bookmarkList;

    public BookmarkListAdpater bookmarkListAdpater;
    public DatabaseReference databaseReference;
    BookmarkFilter bookmarkFilter;

    ValueEventListener valueEventListener;

    public BookmarkFragment(){

    }

    public static BookmarkFragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        bookmarkList = new ArrayList<>();
        this.activity = (HomeActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookmarks")
                .child(app.fireBaseUser);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookmarkList.clear();
                activity.showLoader("downloading bookmarks");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Bookmark bookmark = postSnapshot.getValue(Bookmark.class);
                    bookmarkList.add(bookmark);
                }
                BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, BookmarkFragment.this, bookmarkList);
                listView.setAdapter(trackListAdapter);
                activity.hideLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_bookmark, parent, false);
        getActivity().setTitle("Bookmarks");
        listView = v.findViewById(R.id.bookmarkList);

        updateView();

        return v;
    }

    public void updateView() {
        bookmarkListAdpater = new BookmarkListAdpater(activity, this, bookmarkList);
        bookmarkFilter = new BookmarkFilter(bookmarkList,"all",bookmarkListAdpater);
        listView.setAdapter (bookmarkListAdpater);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Bookmark) {
            Bookmark bookmark=(Bookmark) v.getTag();
            bookmarkListAdpater.bookmarkList.remove(bookmark);
            bookmarkListAdpater.notifyDataSetChanged();
            databaseReference.child(bookmark.getBookmarkId()).removeValue();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
//        Article article = (Article) view.getTag();
        String  bookmarkId= (String) view.getTag();
        Bookmark bookmark =fetchBookmark(bookmarkId);

        if(bookmark != null){
            activityInfo.putString("Title" ,bookmark.getTitle());
            activityInfo.putString("Source" , bookmark.getSource());
            activityInfo.putString("from" , "bookmarkpage");
            activityInfo.putString("url" , bookmark.getUrl());
        }else{
            activityInfo.putString("Title" ,"Sample title");
            activityInfo.putString("Source" , "article source");
            activityInfo.putString("from" , "bookmarkpage");
            activityInfo.putString("url" , "https://www.google.co.in/");
        }


        Fragment fragment = ArticlePageFragment.newInstance(activityInfo);
        getActivity().setTitle("The Print");

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    private Bookmark fetchBookmark(String bookmarkId) {

        for (Bookmark bookmark: bookmarkList ) {
            if(bookmarkId.equals(bookmark.getBookmarkId())){
                return bookmark;
            }
        }

        return null;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_item_bookmark_remove:
                deleteBookmarks(mode);
                return true;
            default:
                return false;
        }
    }

    private void deleteBookmarks(ActionMode mode) {
        activity.showLoader("Removing bookmarks");
        for (int i = bookmarkListAdpater.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i)) {
                Bookmark bookmark =bookmarkListAdpater.getItem(i);
                bookmarkList.remove(bookmark);
                databaseReference.child(bookmark.getBookmarkId()).removeValue();

            }
        }
        bookmarkListAdpater.notifyDataSetChanged(); // refresh adapter
        mode.finish();
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
