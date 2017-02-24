package com.example.edityomurti.moviescrunch.networking;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edityomurti on 2/24/17.
 */

public class RestAdapter{
    private static final String baseUrl = "https://api.themoviedb.org/3/";
    private Retrofit retrofit;
    private Endpoint endpoint;

    public RestAdapter() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        endpoint = retrofit.create(Endpoint.class);
    }

    public Endpoint getEndpoint(){
        return endpoint;
    }
}
