package com.stanleylalanne.imovieapp.app.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.stanleylalanne.imovieapp.R;
import com.stanleylalanne.imovieapp.api.model.Images;
import com.stanleylalanne.imovieapp.api.model.Movie;
import com.stanleylalanne.imovieapp.app.App;
import com.stanleylalanne.imovieapp.app.detail.DetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.stanleylalanne.imovieapp.app.detail.DetailActivity.MOVIE_ID;
import static com.stanleylalanne.imovieapp.app.detail.DetailActivity.MOVIE_TITLE;

public class MainActivity extends AppCompatActivity implements
        MainContract.View,
        SwipeRefreshLayout.OnRefreshListener, ScrollListener.ScrollToBottomListener, ViewAdapter.ItemClickListener {
    private static final String TAG = "Main";

    @Inject
    MoviePresenter presenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView contentView;
    @BindView(R.id.textView)
    View errorView;
    @BindView(R.id.progressBar)
    View loadingView;

    private ViewAdapter viewAdapter;
    private ScrollListener scrollListener;
    private Images images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupContentView();
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .mainModule(new MainModule(this))
                .build()
                .inject(this);


    }


    /*   MENU IMPLEMENTATION */


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.filter:
                Intent i = new Intent(this,Filter.class);
                this.startActivity(i);
                return true;
            case R.id.search:
                Intent i2 = new Intent(this,Search.class);
                this.startActivity(i2);
                return true;
            case R.id.bookmark:
                Intent i3 = new Intent(this,Bookmark.class);
                this.startActivity(i3);
                return true;
            case R.id.clear:
                Intent i4 = new Intent(this,ClearSettings.class);
                this.startActivity(i4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /* END OF MENU IMPLEMENTATION */

    private void setupContentView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scrollListener = new ScrollListener(linearLayoutManager, this);
        contentView.setLayoutManager(linearLayoutManager);
        contentView.addOnScrollListener(scrollListener);
    }

    @Override
    public void onRefresh() {
        scrollListener.onRefresh();
        presenter.onPullToRefresh();
    }

    @Override
    public void onScrollToBottom() {
        presenter.onScrollToBottom();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showLoading(boolean isRefresh) {
        if (isRefresh) {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        } else {
            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContent(List<Movie> movies, boolean isRefresh) {
        if (viewAdapter == null) {
            viewAdapter = new ViewAdapter(movies, this, images, this);
            contentView.setAdapter(viewAdapter);
        } else {
            if (isRefresh) {
                viewAdapter.clear();
            }
            viewAdapter.addAll(movies);
            viewAdapter.notifyDataSetChanged();
        }

        // Delay SwipeRefreshLayout animation by 1.5 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConfigurationSet(Images images) {
        this.images = images;

        if (viewAdapter != null) {
            viewAdapter.setImages(images);
        }
    }

    @Override
    public void onItemClick(int movieId, String movieTitle) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(MOVIE_ID, movieId);
        i.putExtra(MOVIE_TITLE, movieTitle);
        startActivity(i);


    }

    @OnClick(R.id.textView)
    void onClickErrorView() {
        presenter.start();
    }

}
