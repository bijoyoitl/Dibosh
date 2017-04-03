package com.optimalbd.dibosh;

import com.optimalbd.dibosh.NewsModel.NewsMain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ripon on 3/30/2017.
 */

public interface NewsApi {

    @GET()
    Call<NewsMain> getAllNews(@Url String s);
}
