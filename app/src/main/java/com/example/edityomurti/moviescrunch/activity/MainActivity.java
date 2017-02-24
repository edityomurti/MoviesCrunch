package com.example.edityomurti.moviescrunch.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edityomurti.moviescrunch.R;
import com.example.edityomurti.moviescrunch.adapter.MoviesRVAdapter;
import com.example.edityomurti.moviescrunch.model.Movies;
import com.example.edityomurti.moviescrunch.model.MoviesResult;
import com.example.edityomurti.moviescrunch.networking.RestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
        private static final String API_KEY = "6f3824bdde09cbebb021d58882925ccb";
    private static final String LANGUAGE = "en-US";
    private static final String PAGE = "1";

    private List<MoviesResult> moviesResults = new ArrayList<>();
    private RestAdapter restAdapter;

    private RecyclerView recyclerView;
    private MoviesRVAdapter moviesRVAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRVAdapter = new MoviesRVAdapter(getApplicationContext(), moviesResults);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(moviesRVAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesResults.clear();
                fetchData();
                moviesRVAdapter.notifyDataSetChanged();
            }
        });

        restAdapter = new RestAdapter();
        fetchData();
    }

    private void fetchData(){
        restAdapter.getEndpoint().callMovies(API_KEY, LANGUAGE, PAGE).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    moviesResults.addAll(response.body().getResults());
                    moviesRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Load data error!", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "LOAD ERROR : " + t.getMessage());
            }
        });
    }
}
