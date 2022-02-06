package com.example.pinstagram.Relations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Following extends AppCompatActivity implements FollowingAdapter.FollowingListInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");
        Retrofit retrofit = MainBuilder.getInstance();
        Call<List<String>> following = retrofit.create(MainInterface.class).findFollowingByUserId(userId);
        following.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code()==200||response.code()==201){
                RecyclerView recyclerView = findViewById(R.id.recycle_following);
                FollowingAdapter followingAdapter = new FollowingAdapter(response.body(), getApplicationContext(),Following.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(followingAdapter);}
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
}