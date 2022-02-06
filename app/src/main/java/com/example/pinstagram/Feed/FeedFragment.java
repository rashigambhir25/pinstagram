package com.example.pinstagram.Feed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pinstagram.Comments.Comments;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.Post.Post;
import com.example.pinstagram.Post.StoryUpload;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedFragment extends Fragment implements InstagramFeedRVAdapter.InstaModalInterface,StoriesAdapter.StoryDataInterface {

   private RecyclerView storyBar;
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");
        Retrofit retrofit = MainBuilder.getInstance();

        //For Story
        Call<List<Story>> stories = retrofit.create(MainInterface.class).getStoryByUserId(userId);
        stories.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if(response.code()==200||response.code()==201){
                    RecyclerView recyclerView1 = getView().findViewById(R.id.storiesBar);
                    StoriesAdapter storyViewAdapter = new StoriesAdapter(response.body(), FeedFragment.this);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerView1.setAdapter(storyViewAdapter);
                }
                else{
                    Toast.makeText(getContext(), "Sever Down Story", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),NullDataIssue.class));
                }

            }
            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {

            }
        });

        //For Post
        Call<List<PostDto>> posts = retrofit.create(MainInterface.class).getFeedByUserId(userId);
        posts.enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(Call<List<PostDto>> call, Response<List<PostDto>> response) {
                if(response.code()==200||response.code()==201){
                RecyclerView recyclerView = getView().findViewById(R.id.idRVInstaFeeds);
                InstagramFeedRVAdapter instagramFeedRVAdapter = new InstagramFeedRVAdapter(response.body(), FeedFragment.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(instagramFeedRVAdapter);
                Toast.makeText(getContext(), "Feed Loaded", Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(getContext(), "Server Down Post", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),NullDataIssue.class));
                }
            }
            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {

            }
        });

//        List<Story> data = loadStoryData();
//        RecyclerView recyclerView1 = view.findViewById(R.id.storiesBar);
//        StoriesAdapter storyViewAdapter = new StoriesAdapter(data, FeedFragment.this);
//        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerView1.setAdapter(storyViewAdapter);
    }
    @Override
    public void onStoryClick(Story story) {
        Intent intent = new Intent(getContext(), StoryUpload.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("story", story);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    @Override
    public void onUserClick(PostDto postDto, View v, int position) {
        Intent intent = new Intent(getContext(), Post.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("post", postDto);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}