package com.wit.magazine.main;

import com.wit.magazine.api.ArticleService;
//import ie.cm.api.CoffeeService;
import com.wit.magazine.models.Article;
//import ie.cm.models.Coffee;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MagazineApp extends Application
{
    private static MagazineApp mInstance;

    public List <Article>  articleList = new ArrayList<Article>();
    public List <Article>  articleListTab2 = new ArrayList<Article>();
    public List <Article>  articleListTab3 = new ArrayList<Article>();
    public String fireBaseUser;
    public String fireBaseUserName;
    public ArticleService articleService;
    public String articleServiceURL = "https://newsapi.org/v2/";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Gson gson = new GsonBuilder().create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();



        Retrofit articleRetrofit = new Retrofit.Builder()
                .baseUrl(articleServiceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        articleService = articleRetrofit.create(ArticleService.class);
    }

    public static synchronized MagazineApp getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}