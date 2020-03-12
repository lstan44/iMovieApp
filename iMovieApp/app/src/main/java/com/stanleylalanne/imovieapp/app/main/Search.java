package com.stanleylalanne.imovieapp.app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.stanleylalanne.imovieapp.R;
import com.stanleylalanne.imovieapp.api.ApiService;


public class Search extends AppCompatActivity {

    private ApiService api;
    public static String searchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    public void searchButton(View view) {
        EditText searchEt = findViewById(R.id.searchEt);
        MoviePresenter.searchQ= searchEt.getText().toString();
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }


}




