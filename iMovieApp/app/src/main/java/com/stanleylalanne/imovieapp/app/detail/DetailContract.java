package com.stanleylalanne.imovieapp.app.detail;

import com.stanleylalanne.imovieapp.api.model.Images;
import com.stanleylalanne.imovieapp.api.model.Movie;


public interface DetailContract {

    interface View {

        void showLoading();

        void showContent(Movie movie);

        void showError();

        void onConfigurationSet(Images images);

    }

    interface Presenter {

        void start(int movieId);

        void finish();

    }

}
