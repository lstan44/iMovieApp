package com.stanleylalanne.imovieapp.app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stanleylalanne.imovieapp.R;

public class ClearSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_settings);
    }

    public void clearSettings(View view){
        Intent i = new Intent(this,MainActivity.class);
        MoviePresenter.params.clear();
        MoviePresenter.searchQ="";
        startActivity(i);
    }
}
