package com.stanleylalanne.imovieapp.app.detail;

import android.widget.RatingBar;

import com.stanleylalanne.imovieapp.R;
import com.stanleylalanne.imovieapp.api.ApiService;
import com.stanleylalanne.imovieapp.api.model.Configs;
import com.stanleylalanne.imovieapp.api.model.Images;
import com.stanleylalanne.imovieapp.api.model.Movie;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetail implements DetailContract.Presenter {
    private DetailContract.View view;
    private ApiService apiService;

    private Images images;

    @Inject
    MovieDetail(DetailContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void start(int movieId) {
        view.showLoading();

        if (images == null) {
            getConfiguration(movieId);
        } else {
            view.onConfigurationSet(images);
            getMovie(movieId);
        }
    }

    private void getConfiguration(final int movieId) {
        Call<Configs> call = apiService.getConfiguration();
        call.enqueue(new Callback<Configs>() {
            @Override
            public void onResponse(Call<Configs> call, Response<Configs> response) {
                if (response.isSuccessful()) {
                    images = response.body().images;
                    view.onConfigurationSet(images);
                    getMovie(movieId);
                }
            }

            @Override
            public void onFailure(Call<Configs> call, Throwable t) {
            }
        });
    }

    private void getMovie(int movieId) {
        Call<Movie> call = apiService.getMovie(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    view.showContent(response.body());
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                view.showError();
            }
        });
    }

    @Override
    public void finish() {

    }

}
