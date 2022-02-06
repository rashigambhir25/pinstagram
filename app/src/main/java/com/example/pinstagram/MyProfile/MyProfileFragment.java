package com.example.pinstagram.MyProfile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinstagram.Login.Login;
import com.example.pinstagram.Login.LogoutDto;
import com.example.pinstagram.Post.Post;
import com.example.pinstagram.Post.StoryImage;
import com.example.pinstagram.Post.StoryVideo;
import com.example.pinstagram.R;
import com.example.pinstagram.MyProfile.CustomAdapter;
import com.example.pinstagram.Register.StatusDto;
import com.example.pinstagram.Relations.Followers;
import com.example.pinstagram.Relations.Following;
import com.example.pinstagram.RetrofitMain.CRMBuilder;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyProfileFragment extends Fragment {

    Button follower,following,post,story,logout;
    TextView noOfFollowing,noOfFollowers;
    public MyProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");
        TextView username = getView().findViewById(R.id.userName_profile);
        username.setText(userId);
        Retrofit retrofit = MainBuilder.getInstance();
        Call<List<PostDto>> posts = retrofit.create(MainInterface.class).getPostByUserId(userId);
        posts.enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(Call<List<PostDto>> call, Response<List<PostDto>> response) {
                if(response.code()==200||response.code()==201){
                    GridView gridView;
                    gridView = getView().findViewById(R.id.grid_profile);
                    CustomAdapter customAdapter = new CustomAdapter(response.body(),getContext());
                    gridView.setAdapter(customAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getContext(), Post.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("post", response.body().get(i));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "No posts", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {

            }
        });

        follower = getView().findViewById(R.id.followers);
        following = getView().findViewById(R.id.following);
        post = getView().findViewById(R.id.button_post);
        noOfFollowing = getView().findViewById(R.id.no_of_following_frag);
        noOfFollowers = getView().findViewById(R.id.no_of_followers_frag);
        story = getView().findViewById(R.id.button_story);

        //setCountOnFollow
        Call<List<Long>> counts=retrofit.create(MainInterface.class).getNoOfConnections(userId);
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

        follower.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), Followers.class));
        });

        following.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), Following.class));
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog postMedia = new Dialog(getContext());
//                postMedia.setTitle("Post");
                postMedia.setContentView(R.layout.posting_option);
                Button image = (Button)postMedia.findViewById(R.id.button_image);
                Button video = (Button)postMedia.findViewById(R.id.button_video);
                image.setOnClickListener(view1 -> {
                    startActivity(new Intent(getContext(),PostImage.class));
                });
                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext() , PostVideo.class);
                        startActivity(intent);

                    }
                });
                postMedia.show();
            }
        });
        story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog postMedia = new Dialog(getContext());
//                postMedia.setTitle("Post");
                postMedia.setContentView(R.layout.posting_option);
                Button image = (Button)postMedia.findViewById(R.id.button_image);
                Button video = (Button)postMedia.findViewById(R.id.button_video);
                image.setOnClickListener(view1 -> {
                    startActivity(new Intent(getContext(), StoryImage.class));
                });
                video.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext() , StoryVideo.class);
                        startActivity(intent);
                    }
                });
                postMedia.show();
            }
        });
        logout = getView().findViewById(R.id.logout);

        logout.setOnClickListener(view1 -> {
            Retrofit retrofitCrm= CRMBuilder.getInstance();
            LogoutDto logoutDto=new LogoutDto();
            String jwt = sharedPreferences.getString("jwt","B");
            logoutDto.setUserEmail(userId);
            logoutDto.setJwt(jwt);
            logoutDto.setAppId("2");


            Call<StatusDto> statusDtoCall=retrofitCrm.create(MainInterface.class).logout(logoutDto);
            statusDtoCall.enqueue(new Callback<StatusDto>() {
                @Override
                public void onResponse(Call<StatusDto> call, Response<StatusDto> response) {
                    Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), Login.class));
                }

                @Override
                public void onFailure(Call<StatusDto> call, Throwable t) {

                }
            });
        });

    }
}