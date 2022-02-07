package com.example.pinstagram.Relations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pinstagram.Feed.FeedFragment;
import com.example.pinstagram.Feed.InstaModel;
import com.example.pinstagram.Feed.InstagramFeedRVAdapter;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Followers extends AppCompatActivity implements FollowersAdapter.FollowersListInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit = MainBuilder.getInstance();
        Call<List<String>> followers = retrofit.create(MainInterface.class).findFollowerByUserId(userId,"following");
        followers.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code()==200||response.code()==201){
                RecyclerView recyclerView = findViewById(R.id.recycle_followers);
                FollowersAdapter followersAdapter = new FollowersAdapter(response.body(), getApplicationContext(),Followers.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(followersAdapter);}
                else{
                    Toast.makeText(getApplicationContext(), "Server Down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onUserClick(String followersModel) {

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}