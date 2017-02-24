package com.example.edityomurti.moviescrunch.networking;

import com.example.edityomurti.moviescrunch.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by edityomurti on 2/24/17.
 */

public interface Endpoint {
    @GET("movie/popular")
    Call<Movies> callMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") String page);
}
