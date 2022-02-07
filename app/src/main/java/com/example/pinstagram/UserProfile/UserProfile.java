package com.example.pinstagram.UserProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinstagram.MyProfile.CustomAdapter;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.Post.Post;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfile extends AppCompatActivity {

    TextView userName,noOfFollowers,noOfFollowing;
    Button followBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        UserDto targetDto = (UserDto) bundle.getSerializable("target");

        userName=findViewById(R.id.userName_profile_t);
        noOfFollowing=findViewById(R.id.no_of_following_t);
        noOfFollowers=findViewById(R.id.no_of_followers2_t);
        followBtn=findViewById(R.id.button_follow_t);

        userName.setText(targetDto.getName());

        Retrofit retrofit=MainBuilder.getInstance();

        //setCountOnFollow
        Call<List<Long>> counts=retrofit.create(MainInterface.class).getNoOfConnections(targetDto.getId());
        counts.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                noOfFollowers.setText(response.body().get(1)+"");
                noOfFollowing.setText(response.body().get(0)+"");
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {

            }
        });

        //check connection
        Call<Boolean> bool=retrofit.create(MainInterface.class).checkConnection(userId, targetDto.getId());
        bool.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    followBtn.setText("Following");
                }
                else{
                    followBtn.setText("Follow");
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

        //toggle connection
        followBtn.setOnClickListener(view -> {
            String state=followBtn.getText().toString();

            if(state.equals("Following")){
                Call<Void> unfollow =retrofit.create(MainInterface.class).deleteConnection(userId,targetDto.getId());
                unfollow.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
            else{
                ConnectionDto connectionDto = new ConnectionDto();
                connectionDto.setConnectionType("following");
                connectionDto.setUserEmail(userId);
                connectionDto.setTargetEmail(targetDto.getId());
                //Time
                long unixTime = System.currentTimeMillis() / 1000L;

                Call<Void> follow=retrofit.create(MainInterface.class).addConnection(connectionDto);
                follow.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Following", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });

//        grid View
        Call<List<PostDto>> posts = retrofit.create(MainInterface.class).getPostByUserId(targetDto.getId());
        posts.enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(Call<List<PostDto>> call, Response<List<PostDto>> response) {
                if(response.code()==200||response.code()==201){
                    GridView gridView;
                    gridView = findViewById(R.id.grid_profile_t);
                    CustomAdapter customAdapter = new CustomAdapter(response.body(),getApplicationContext());
                    gridView.setAdapter(customAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getApplicationContext(), Post.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("post", response.body().get(i));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "No posts", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {

            }
        });





//        Retrofit retrofit = MainBuilder.getInstance();
//        Call<List<>>

//        List<FollowersModel> followersModel=new ArrayList<>();
//        TextView username = findViewById(R.id.userName_profile);
//        if(followersModel.getUserName()!=username){
//        }
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