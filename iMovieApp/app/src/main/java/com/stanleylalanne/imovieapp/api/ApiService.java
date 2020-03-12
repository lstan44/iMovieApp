package com.stanleylalanne.imovieapp.api;

import com.stanleylalanne.imovieapp.api.model.Configs;
import com.stanleylalanne.imovieapp.api.model.Movie;
import com.stanleylalanne.imovieapp.api.model.Movies;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiService {
    enum SortBy {
        RELEASE_DATE_ASCENDING("release_date.asc"),
        RELEASE_DATE_DESCENDING("release_date.desc");

        String value;

        SortBy(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return this.value;
        }
    }

    @GET("/3/discover/movie")
    Call<Movies> getMovies(@Query("primary_release_date.lte") String releaseDate,
                           @Query("sort_by") SortBy sortBy, @Query("page") int page, @Query("release_date.lte") String rDate);

    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") int id);

    @GET("/3/search/movie")
    Call<Movies> getMovies(@Query("query") String query, @Query("adult") Boolean adult);

    @GET("/3/discover/movie")
    Call<Movies> getMovies(@QueryMap Map<String,String> params);

    @Headers("Cache-Control: public, max-stale=24192")
    @GET("/3/configuration")
    Call<Configs> getConfiguration();

}
