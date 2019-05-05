package com.wit.magazine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.adapters.FriendsAdapter;
import com.wit.magazine.adapters.SocialFeedAdapter;
import com.wit.magazine.models.Article;
import com.wit.magazine.models.SharedArticle;
import com.wit.magazine.models.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class SocialFeedFragment extends Fragment implements View.OnClickListener {

    public HomeActivity activity;
    private RecyclerView mResultList;
    List<SharedArticle> sharedList;
    private DatabaseReference mUserDatabase;

    public SocialFeedFragment(){

    }

    public static SocialFeedFragment newInstance() {
        SocialFeedFragment fragment = new SocialFeedFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        sharedList =new ArrayList<>();
        this.activity = (HomeActivity) context;

//        NewsAPI.attachDialog(activity.loader);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("ArticleShares");

        activity.showLoader("downloading feed");
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sharedList.clear();
                Log.v("magazine","Search text in onQueryTextChange : "+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SharedArticle usershare = postSnapshot.getValue(SharedArticle.class);
                    sharedList.add(usershare);
                }
//                BookmarkListAdpater trackListAdapter = new BookmarkListAdpater(activity, BookmarkFragment.this, bookmarkList);
//                listView.setAdapter(trackListAdapter);
                Log.v("magazine","Search text in onQueryUserList : "+String.valueOf(sharedList.size()));
                SocialFeedAdapter socialFeedAdapter = new SocialFeedAdapter(getContext(), sharedList, SocialFeedFragment.this);
                mResultList.setAdapter(socialFeedAdapter);
                activity.hideLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_socialfeed, container, false);
        getActivity().setTitle("Social feed");
        mResultList = (RecyclerView) v.findViewById(R.id.sharedList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this.activity));

        SocialFeedAdapter socialFeedAdapter = new SocialFeedAdapter(this.activity, sharedList,this);
        mResultList.setAdapter(socialFeedAdapter);

        return v;
    }


    @Override
    public void onClick(View v) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        SharedArticle article = (SharedArticle) v.getTag();

//        activityInfo.putStringArray();
        activityInfo.putString("Title" , article.getTitle());
        activityInfo.putString("Source" , article.getSource());
        activityInfo.putString("from" ,"feedpage" );
        activityInfo.putString("url" , article.getUrl());

        Fragment fragment = ArticlePageFragment.newInstance(activityInfo);
        getActivity().setTitle(article.getSource());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
