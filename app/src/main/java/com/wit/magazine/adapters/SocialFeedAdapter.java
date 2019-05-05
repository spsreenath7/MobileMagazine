package com.wit.magazine.adapters;

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
import com.wit.magazine.models.SharedArticle;
import com.wit.magazine.models.UserProfile;

import java.util.List;

public class SocialFeedAdapter extends RecyclerView.Adapter<SocialFeedAdapter.ShareViewHolder> {

    private Context mCtx;

    private List<SharedArticle> shareList;
    private View.OnClickListener onClickListener;

    public SocialFeedAdapter(Context mCtx, List<SharedArticle> shareList, View.OnClickListener onClickListener) {
        Log.v("coffeemate","ARTICLES SIZE : "+String.valueOf(shareList.size()) );
        this.mCtx = mCtx;
        this.shareList = shareList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SocialFeedAdapter.ShareViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.sharedcard, null);
        return new SocialFeedAdapter.ShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialFeedAdapter.ShareViewHolder friendViewHolder, int position) {
        SharedArticle article = shareList.get(position);

        friendViewHolder.textViewUsername.setText(article.getUsername());
        friendViewHolder.textViewSource.setText(article.getSource());
        friendViewHolder.textViewTitle.setText(article.getTitle());

        friendViewHolder.textViewTitle.setTag(article);
        friendViewHolder.textViewTitle.setOnClickListener(this.onClickListener);

    }

    @Override
    public int getItemCount() {
        return shareList.size();
    }

//    @Override
//    public UserProfile getItem(int position) {
//        return friendList.get(position);
//    }

    public class ShareViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUsername, textViewSource, textViewTitle;
        RecyclerView feedRecyclerView;
        Button follow;

        public ShareViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.textViewSharedby);
            textViewSource = itemView.findViewById(R.id.textViewArticlesource);
            textViewTitle = itemView.findViewById(R.id.textViewArticleTitle);
            feedRecyclerView = itemView.findViewById(R.id.feedRecyclerView);
//            follow = itemView.findViewById(R.id.buttonConnect);
        }
    }
}
