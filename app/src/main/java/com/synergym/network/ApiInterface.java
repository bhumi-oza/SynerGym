package com.synergym.network;


import com.synergym.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("everything")
    Call<NewsModel> getNewsList(@Query("q") String q,
                                @Query("from") String from, @Query("sortBy") String sortBy,
                                @Query("apiKey") String apiKey);


}


