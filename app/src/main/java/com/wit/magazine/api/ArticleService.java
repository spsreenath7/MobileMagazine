package com.wit.magazine.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService {
//    https://newsapi.org/v2/top-headlines?apiKey=55cab82b7638477aa1b2321668481bac&country=ie&pageSize=5

    String NEWSAPI_KEY = "55cab82b7638477aa1b2321668481bac";

    @GET("top-headlines")
    Call<ArticleWrapper> getAllTrending(@Query("apiKey") String apiKey, @Query("country") String country, @Query("pageSize") int pageSize);

//    @GET("top-headlines")
//    Call<ArticleWrapper> getAllTrending(@Query("apiKey") String apiKey, @Query("pageSize") int pageSize, @Query("sources") String sources);
    @GET("top-headlines")
    Call<ArticleWrapper> getMostRead(@Query("apiKey") String apiKey, @Query("sources") String sources, @Query("pageSize") int pageSize);

    @GET("top-headlines")
    Call<ArticleWrapper> getGenral(@Query("apiKey") String apiKey, @Query("sources") String sources, @Query("pageSize") int pageSize);

}
