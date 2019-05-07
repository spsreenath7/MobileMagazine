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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wit.magazine.activities.HomeActivity.app;

public class MostreadFragment extends TrendingFragement implements Callback<ArticleWrapper>
{

    public MostreadFragment(){

    }

    public static MostreadFragment newInstance() {
        MostreadFragment fragment = new MostreadFragment();

        return fragment;
    }

    @Override
    public void getAll() {
        activity.showLoader("Downloading Articles...");
        String catogery = "business,entertainment,general,health,science,sports,technology";
        callRetrieve = app.articleService.getMostRead(ArticleService.NEWSAPI_KEY,"world", app.userPreference.getArticlecount());
        Log.v("magazine","Most read call : "+callRetrieve.request().url().toString());
        callRetrieve.enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mostread, container, false);
//        getActivity().setTitle("Recommedations");
        recyclerView = v.findViewById(R.id.recyclerViewMostRead);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.activity));

        updateView(app.articleListTab2);

        return v;
    }

    @Override
    public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {
        ArticleWrapper cw = response.body();
        app.articleListTab2 = cw.articles;
        updateView(app.articleListTab2);
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
