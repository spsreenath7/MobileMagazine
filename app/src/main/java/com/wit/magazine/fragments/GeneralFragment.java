package com.wit.magazine.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wit.magazine.R;
import com.wit.magazine.api.ArticleService;
import com.wit.magazine.api.ArticleWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wit.magazine.activities.HomeActivity.app;

public class GeneralFragment extends TrendingFragement implements Callback<ArticleWrapper> {

    public GeneralFragment(){

    }

    public static GeneralFragment newInstance() {
        GeneralFragment fragment = new GeneralFragment();

        return fragment;
    }

    @Override
    public void getAll() {
        activity.showLoader("Downloading Articles...");
        callRetrieve = app.articleService.getGenral(ArticleService.NEWSAPI_KEY,"bbc-news,bloomberg,business-insider", 5);
        callRetrieve.enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("coffeemate","COFFEE onCreateView START : " );

        View v = inflater.inflate(R.layout.fragment_general, container, false);
//        getActivity().setTitle("Recommedations");
        recyclerView = v.findViewById(R.id.recyclerViewGeneral);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));

        updateView(app.articleListTab3);
        Log.v("coffeemate","COFFEE onCreateView END : " );

        return v;
    }

    @Override
    public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {
        ArticleWrapper cw = response.body();
        app.articleListTab3 = cw.articles;
        updateView(app.articleListTab3);
//        checkSwipeRefresh(v);
        activity.hideLoader();
    }

    @Override
    public void onFailure(Call<ArticleWrapper> call, Throwable t) {
        t.printStackTrace();
        Toast.makeText(getActivity(),
                "Service Unavailable. Try again later",
                Toast.LENGTH_LONG).show();
    }
}
