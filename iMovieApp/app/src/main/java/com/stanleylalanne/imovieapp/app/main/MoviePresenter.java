package com.stanleylalanne.imovieapp.app.main;

import android.annotation.SuppressLint;
import android.support.annotation.VisibleForTesting;

import com.stanleylalanne.imovieapp.api.ApiService;
import com.stanleylalanne.imovieapp.api.model.Configs;
import com.stanleylalanne.imovieapp.api.model.Movies;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MoviePresenter implements MainContract.Presenter {
    public static String searchQ="";
    public static String genre = "";
    public static String rYear = "";
    public static String adult = "";
    public static String voteCount = "";
    public static String popularity = "";

    public static Map<String,String> params = new HashMap<>();
    private MainContract.View view;
    private ApiService apiService;

    private int page = 1;
    private Configs configs;

    @Inject
    MoviePresenter(MainContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void start() {
        view.showLoading(false);
        getMovies(true);
        getConfiguration();
    }

    private void getConfiguration() {
        Call<Configs> call = apiService.getConfiguration();
        call.enqueue(new Callback<Configs>() {
            @Override
            public void onResponse(Call<Configs> call, Response<Configs> response) {
                if (response.isSuccessful()) {
                    view.onConfigurationSet(response.body().images);
                }
            }

            @Override
            public void onFailure(Call<Configs> call, Throwable t) {
            }
        });
    }

    @Override
    public void onPullToRefresh() {
        page = 1; // reset
        view.showLoading(true);
        getMovies(true);
    }

    @Override
    public void onScrollToBottom() {
        page++;
        view.showLoading(true);
        getMovies(false);
    }

    private void getMovies(final boolean isRefresh) {
        String rDate = "2019";

        Call<Movies> call = apiService.getMovies(getReleaseDate(),
                ApiService.SortBy.RELEASE_DATE_DESCENDING, page, rDate);

        if(!searchQ.isEmpty()){
            call = apiService.getMovies(searchQ,true);
        }

        if(!params.isEmpty()){
            call = apiService.getMovies(params);
        }
            call.enqueue(new Callback<Movies>() {
                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.isSuccessful()) {
                        view.showContent(response.body().movies, isRefresh);
                    } else {
                        view.showError();
                    }
                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {
                    view.showError();
                }
            });

        }



    @VisibleForTesting
    public String getReleaseDate() {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return format1.format(cal.getTime());
    }

    @Override
    public void finish() {

    }

}
