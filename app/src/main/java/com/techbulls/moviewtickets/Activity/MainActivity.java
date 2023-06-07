package com.techbulls.moviewtickets.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.techbulls.moviewtickets.Adapter.MovieAdapter;
import com.techbulls.moviewtickets.Extra.AutoFitGridRecyclerView;
import com.techbulls.moviewtickets.Model.MovieResponse;
import com.techbulls.moviewtickets.Model.SearchByMovieResponse;
import com.techbulls.moviewtickets.R;
import com.techbulls.moviewtickets.Retrofit.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    AutoFitGridRecyclerView recyclerView;
    TextInputEditText textInputEditText;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ImageView searchIcon;
    List<SearchByMovieResponse> searchByMovieResponseList = new ArrayList<>();
    int page = 1, maxPage;
    String movieName = "bat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        initViews();

        getMovieList(movieName);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchByMovieResponseList.clear();
                page = 1;
                getMovieList(movieName);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //check condition
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                //Increase page size
                page++;
                if (page <= maxPage) {
                    getMovieList(movieName);
                }
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEditText.getText().toString().length() > 2) {
                    searchByMovieResponseList.clear();
                    movieName = textInputEditText.getText().toString();
                    hideKeyboard(v);
                    getMovieList(movieName);
                } else {
                    Toast.makeText(MainActivity.this, "Movie name atleast 3 letter", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void hideKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    private void initViews() {

        recyclerView = findViewById(R.id.recyclerView);
        textInputEditText = findViewById(R.id.searchView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        searchIcon = findViewById(R.id.searchIcon);

    }

    private void getMovieList(String movieName) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<MovieResponse> call = Api.getClient().getMovieList(movieName, "ccc8db3", page, "10");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getResponse().equalsIgnoreCase("true")) {
                        searchByMovieResponseList.addAll(response.body().getSearchByMovieResponseList());
                        maxPage = Integer.parseInt(response.body().getTotalResults());
                        Log.e("totalPages", "" + maxPage);
                        if (searchByMovieResponseList.size() > 0) {
                            Log.e("searchByMovieList", "" + searchByMovieResponseList.size());

                            MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, searchByMovieResponseList);
                            recyclerView.setAdapter(movieAdapter);

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "" + response.body().getError(), Toast.LENGTH_SHORT).show();
                    }

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("serverError", "" + t.getMessage());
                progressDialog.dismiss();
            }
        });

    }


}