package com.stanleylalanne.imovieapp.app.detail;

import com.stanleylalanne.imovieapp.app.ActivityScope;

import dagger.Module;
import dagger.Provides;



@Module
public class DetailModule {
    private final DetailContract.View DetailView;

    DetailModule(DetailContract.View DetailView) {
        this.DetailView = DetailView;
    }

    @Provides
    @ActivityScope
    DetailContract.View provideDetailView() {
        return DetailView;
    }

}

