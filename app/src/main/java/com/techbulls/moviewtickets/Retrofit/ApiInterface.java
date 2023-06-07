package com.techbulls.moviewtickets.Retrofit;


import com.techbulls.moviewtickets.Model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/")
    Call<MovieResponse> getMovieList(@Query("s") String movieName,
                                     @Query("apikey") String apikey,
                                     @Query("page") int page,
                                     @Query("pagesize") String pagesize);


}
