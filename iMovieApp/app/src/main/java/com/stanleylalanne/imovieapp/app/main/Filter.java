package com.stanleylalanne.imovieapp.app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.stanleylalanne.imovieapp.R;


public class Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void setFilters(View view){
        EditText year = findViewById(R.id.release_year);
        EditText genre = findViewById(R.id.genre);
        EditText adult = findViewById(R.id.adult);
        EditText voteCount = findViewById(R.id.voteCount);
        EditText popularity = findViewById(R.id.popularity);


        MoviePresenter.voteCount = voteCount.getText().toString();
        MoviePresenter.genre = genre.getText().toString();
        MoviePresenter.rYear = year.getText().toString();
        MoviePresenter.popularity = popularity.getText().toString();
        MoviePresenter.adult = adult.getText().toString();

        String pop = "";
        if(MoviePresenter.popularity.equals("most")|| MoviePresenter.popularity.equals("Most")){
            pop = "popularity.desc";
        }
        else{
            pop = "popularity.asc";
        }
        MoviePresenter.params.put("sort_by",pop);
        MoviePresenter.params.put("without_genres", MoviePresenter.genre);
        MoviePresenter.params.put("year", MoviePresenter.rYear);
        MoviePresenter.params.put("include_adult", MoviePresenter.adult);
        MoviePresenter.params.put("vote_count.gte", MoviePresenter.voteCount);
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);

    }
}
