package com.wit.magazine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.wit.magazine.R;
import com.wit.magazine.activities.HomeActivity;
import com.wit.magazine.adapters.ArticlesAdapter;
import com.wit.magazine.api.ArticleService;
import com.wit.magazine.api.ArticleWrapper;
import com.wit.magazine.models.Article;
import com.wit.magazine.models.Source;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wit.magazine.activities.HomeActivity.app;

public class TrendingFragement extends Fragment implements View.OnClickListener, Callback<ArticleWrapper> {

    public HomeActivity activity;
    //a list to store all the products
//    List<Article> articleList;

    //the recyclerview
    RecyclerView recyclerView;
    public Call<ArticleWrapper> callRetrieve;

    ArticlesAdapter adapter;
    public TrendingFragement(){

    }

    public static TrendingFragement newInstance() {
        TrendingFragement fragment = new TrendingFragement();

        return fragment;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (HomeActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAll();

    }

    public void getAll() {
        activity.showLoader("Downloading Articles...");
        callRetrieve = app.articleService.getAllTrending(ArticleService.NEWSAPI_KEY,app.userPreference.getCountrycode(), app.userPreference.getArticlecount());
        callRetrieve.request().url();
        callRetrieve.enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_trending, container, false);
        getActivity().setTitle("Recommedations");
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));

        updateView(app.articleList);
        return v;
    }

    @Override
    public void onClick(View view) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        Article article = (Article) view.getTag();

        activityInfo.putString("Title" , article.getTitle());
        activityInfo.putString("Source" , article.getSource().getName());
        activityInfo.putString("from" ,"artcilepage" );
        activityInfo.putString("url" , article.getUrl());
        activityInfo.putString("imageURL", article.getUrlToImage());

        Fragment fragment = ArticlePageFragment.newInstance(activityInfo);
        getActivity().setTitle(article.getSource().getName());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {
        ArticleWrapper cw = response.body();
        app.articleList = cw.articles;
        updateView(app.articleList);
//        checkSwipeRefresh(v);
        activity.hideLoader();
    }

    public void updateView(List<Article> articleList) {
        adapter = new ArticlesAdapter(this.activity, articleList, this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<ArticleWrapper> call, Throwable t) {
        t.printStackTrace();
        Toast.makeText(getActivity(),
                "Service Unavailable. Try again later",
                Toast.LENGTH_LONG).show();
    }
}
