package com.example.pinstagram.SearchList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pinstagram.R;
import com.example.pinstagram.Relations.Following;
import com.example.pinstagram.Relations.FollowingAdapter;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.example.pinstagram.UserProfile.UserDto;
import com.example.pinstagram.UserProfile.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchUserList extends AppCompatActivity implements SearchAdapter.SearchInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_list);

        String query= getIntent().getStringExtra("query");
        Retrofit retrofit= MainBuilder.getInstance();
        Call<List<UserDto>> users=retrofit.create(MainInterface.class).search(query);
        users.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                RecyclerView recyclerView = findViewById(R.id.recycle_search);
                SearchAdapter searchAdapter=new SearchAdapter(response.body(),getApplicationContext(),SearchUserList.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {

            }
        });


    }


    @Override
    public void onUserClick(UserDto userDto, View view, int position) {
        Intent i = new Intent(this, UserProfile.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("target",userDto);
        i.putExtras(bundle);
        startActivity(i);
    }
}