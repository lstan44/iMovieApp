package com.stanleylalanne.imovieapp.app.main;

import com.stanleylalanne.imovieapp.app.ActivityScope;
import com.stanleylalanne.imovieapp.app.AppComponent;

import dagger.Component;


@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = MainModule.class
)
interface MainComponent {

    void inject (MainActivity mainActivity);

}

