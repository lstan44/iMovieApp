package com.stanleylalanne.imovieapp.app.detail;

import com.stanleylalanne.imovieapp.app.ActivityScope;
import com.stanleylalanne.imovieapp.app.AppComponent;
import com.stanleylalanne.imovieapp.app.main.Bookmark;

import dagger.Component;



@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = DetailModule.class
)
interface DetailComponent {

    void inject(DetailActivity DetailActivity);
    //void inject(Bookmark Bookmark);

}

