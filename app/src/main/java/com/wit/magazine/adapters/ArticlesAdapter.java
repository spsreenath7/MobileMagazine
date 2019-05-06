package com.wit.magazine.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.wit.magazine.R;
import com.wit.magazine.models.Article;
//import ie.cm.models.Product;

/**
 * Created by Belal on 10/18/2017.
 */


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Article> articleList;
    private View.OnClickListener onClickListener;

    //getting the context and product list with constructor
    public ArticlesAdapter(Context mCtx, List<Article> articleList, View.OnClickListener onClickListener) {
        Log.v("coffeemate","ARTICLES SIZE : "+String.valueOf(articleList.size()) );
        this.mCtx = mCtx;
        this.articleList = articleList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.articlecard, null);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        //getting the product of the specified position
        Article article = articleList.get(position);
        Log.v("coffeemate","ARTICLE : "+String.valueOf(position) );

        //binding the data with the viewholder views
        holder.textViewTitle.setText(article.getTitle());
        holder.textViewDate.setText(article.getPublishedAt().substring(0,10));
//        article.getPublishedAt().substring(12,16)+" "+
//        holder.textViewPrice.setText("$"+String.valueOf(article.getPrice()));
        holder.textAuthor.setText(String.valueOf(article.getSource().getName()));


        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.youtube_logo));

//        RequestManager requestManager = Glide.with(mCtx);
//// Create request builder and load image.
//        RequestBuilder requestBuilder = requestManager.load(article.getUrlToImage());
//// Show image into target imageview.
//        requestBuilder.into(holder.imageView);
        Glide.with(mCtx)
                .load(article.getUrlToImage())
                .apply(new RequestOptions().placeholder(R.drawable.loginbgimg).error(R.drawable.newslogo))
//                .error(R.drawable.youtube_logo)
                .into(holder.imageView);

        holder.relativeLayout.setTag(article);

        holder.relativeLayout.setOnClickListener(this.onClickListener);

    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDate, textViewPrice, textAuthor;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
//            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            imageView = itemView.findViewById(R.id.imageView);
            relativeLayout =itemView.findViewById(R.id.articleCardLayout);
        }
    }
}