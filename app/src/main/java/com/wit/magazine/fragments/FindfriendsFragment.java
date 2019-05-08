package com.wit.magazine.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
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
import com.wit.magazine.models.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static com.wit.magazine.activities.HomeActivity.app;

public class FindfriendsFragment extends Fragment implements View.OnClickListener{

    public HomeActivity activity;

//    private SearchView mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    private EditText mSearchField;

    List<UserProfile> userList;
    private DatabaseReference mUserDatabase;

    public FindfriendsFragment(){

    }

    public static FindfriendsFragment newInstance() {
        FindfriendsFragment fragment = new FindfriendsFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        userList =new ArrayList<>();
        this.activity = (HomeActivity) context;

//        NewsAPI.attachDialog(activity.loader);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("UserProfile");

        activity.showLoader("downloading users");
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                Log.v("magazine","Search text in onQueryTextChange : "+dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserProfile user = postSnapshot.getValue(UserProfile.class);
                    if(user.getUserid() != app.fireBaseUser){
                        userList.add(user);
                    }
                }
                FriendsAdapter friendsAdapter = new FriendsAdapter(getContext(), userList,app.friendsSet, FindfriendsFragment.this);
                mResultList.setAdapter(friendsAdapter);
                activity.hideLoader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void configureAdapter(List<UserProfile> userList) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_usersearch, container, false);
        getActivity().setTitle("Find friends");


//        mSearchField = (SearchView) v.findViewById(R.id.searchViewFriends);
//        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        mSearchField = (EditText)v.findViewById(R.id.editTextSearchfriends);
        mSearchBtn = (ImageButton)v.findViewById(R.id.imageButtonSearch);

        mResultList = (RecyclerView) v.findViewById(R.id.userList);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this.activity));

        Log.v("magazine","Search text in onQueryUserList : "+String.valueOf(userList.size()));
        FriendsAdapter friendsAdapter = new FriendsAdapter(this.activity, userList, app.friendsSet, this);
        mResultList.setAdapter(friendsAdapter);
//        mResultList.setAdapter(firebaseRecyclerAdapter);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                Log.v("magazine","Search text in onQueryTextChange : "+searchText);
//                firebaseUserSearch(searchText);

            }
        });

//        mSearchField.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                firebaseUserSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                firebaseUserSearch(newText);
//                return false;
//            }
//        });

        return v;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof String) {

            String followedUserid = (String) v.getTag();
//            bookmarkListAdpater.user.remove(user);
//            bookmarkListAdpater.notifyDataSetChanged();
//            databaseBookmarkReference.child(bookmark.getBookmarkId()).removeValue();
            Log.v("magazine","Search text in onQueryTextChange : "+followedUserid);
            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("FriendLists")
                    .child(app.fireBaseUser);
//            dbReference.push();
//            dbReference.setValue(new ArrayList<String>().add(followedUserid));
            if(app.friendsSet.contains(followedUserid)){
                dbReference.child(followedUserid).removeValue();
                app.friendsSet.remove(followedUserid);
                ((Button)v.findViewById(R.id.buttonConnect)).setText("Follow");
                ((Button)v.findViewById(R.id.buttonConnect)).setBackgroundColor(R.color.colorFollow);

            }else {
                dbReference.child(followedUserid).setValue(followedUserid);
                ((Button)v.findViewById(R.id.buttonConnect)).setText("Following");
                ((Button)v.findViewById(R.id.buttonConnect)).setBackgroundColor(R.color.colorFollowing);
            }

//            ((Button)v.findViewById(R.id.buttonConnect)).setClickable(false);

        }
    }

//    private void firebaseUserSearch(String searchText) {
//
////        activity.showLoader("Downloading Articles...");
//
//        Query firebaseSearchQuery = mUserDatabase.orderByChild("username").startAt(searchText);
//
//        FirebaseRecyclerOptions<UserProfile> options =
//                new FirebaseRecyclerOptions.Builder<UserProfile>()
//                        .setQuery(firebaseSearchQuery, UserProfile.class)
//                        .build();
//
//        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserProfile, UsersViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserProfile model) {
//                holder.setDetails(model.getUsername(), model.getEmail(), model.getProfilephotoURL());
//            }
//
//            @Override
//            public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.usercard, parent, false);
//
//                return new UsersViewHolder(view);
//            }
//
//        };
//
//        mResultList.setAdapter(firebaseRecyclerAdapter);
//
//    }
//
//    public static class UsersViewHolder extends RecyclerView.ViewHolder {
//
//        View mView;
//
//        public UsersViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setDetails(String userName, String userEmail, String userImage){
//
//            TextView username = (TextView) mView.findViewById(R.id.textViewCardUsername);
//            TextView useremail = (TextView) mView.findViewById(R.id.textViewCardemail);
//
//            username.setText(userName);
//            useremail.setText(userEmail);
////            Glide.with(ctx).load(userImage).into(user_image);
//        }
//
//    }
}
