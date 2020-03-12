package com.stanleylalanne.imovieapp.app;

import android.app.Application;

import com.stanleylalanne.imovieapp.api.ApiModule;
import com.stanleylalanne.imovieapp.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(
        modules = {
                AppModule.class,
                ApiModule.class
        }
)
public interface AppComponent {

    Application application();

    ApiService apiService();

    void inject(App app);

}

