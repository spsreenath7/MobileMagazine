package com.wit.magazine.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wit.magazine.R;
import com.wit.magazine.models.UserProfile;

import java.util.List;
import java.util.Set;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

    private Context mCtx;
    private Set<String> friendsSet;
    private List<UserProfile> friendList;
    private View.OnClickListener onButtonClickListener;

    public FriendsAdapter(Context mCtx, List<UserProfile> friendList, Set<String> friendsSet, View.OnClickListener onClickListener) {
        Log.v("coffeemate","ARTICLES SIZE : "+String.valueOf(friendList.size()) );
        this.mCtx = mCtx;
        this.friendList = friendList;
        this.friendsSet =friendsSet;
        this.onButtonClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FriendsAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.usercard, null);
        return new FriendsAdapter.FriendViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.FriendViewHolder friendViewHolder, int position) {
        UserProfile friend = friendList.get(position);

        friendViewHolder.textViewUsername.setText(friend.getUsername());
        friendViewHolder.textViewEmail.setText(friend.getEmail());

        if(!friendsSet.contains(friend.getUserid())){
            friendViewHolder.follow.setTag(friend.getUserid());
            friendViewHolder.follow.setOnClickListener(this.onButtonClickListener);
        }else {
            friendViewHolder.follow.setText("Following");
            friendViewHolder.follow.setBackgroundColor(R.color.colorAccent);
        }

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

//    @Override
//    public UserProfile getItem(int position) {
//        return friendList.get(position);
//    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUsername, textViewEmail;
        Button follow;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.textViewCardUsername);
            textViewEmail = itemView.findViewById(R.id.textViewCardemail);
            follow = itemView.findViewById(R.id.buttonConnect);
        }
    }
}
