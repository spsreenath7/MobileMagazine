package com.wit.magazine.main;

import com.wit.magazine.api.ArticleService;
import com.wit.magazine.models.Article;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wit.magazine.models.UserPreference;

public class MagazineApp extends Application
{
    private static MagazineApp mInstance;

    public List <Article>  articleList = new ArrayList<Article>();
    public List <Article>  articleListTab2 = new ArrayList<Article>();
    public List <Article>  articleListTab3 = new ArrayList<Article>();
    public String fireBaseUser;
    public String fireBaseUserName;
    public String fireBaseUserEmail;
    public Set<String> friendsSet = new HashSet<>();
    public UserPreference userPreference;
    public ArticleService articleService;
    public String articleServiceURL = "https://newsapi.org/v2/";

    public Location mCurrentLocation;

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