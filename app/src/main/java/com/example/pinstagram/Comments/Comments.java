package com.example.pinstagram.Comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinstagram.Feed.FeedFragment;
import com.example.pinstagram.Feed.InstagramFeedRVAdapter;
import com.example.pinstagram.Post.StoryImage;
import com.example.pinstagram.Post.StoryVideo;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Comments extends AppCompatActivity implements CommentAdapter.CommentDtoInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");
        Long postId = getIntent().getLongExtra("postId",10000000000L);
        Retrofit retrofit = MainBuilder.getInstance();
        Call<List<CommentDto>> comments = retrofit.create(MainInterface.class).getCommentsForPost(postId);
        comments.enqueue(new Callback<List<CommentDto>>() {
            @Override
            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                CommentAdapter commentAdapter= new CommentAdapter(response.body(), getApplicationContext(),Comments.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(commentAdapter);
            }
            @Override
            public void onFailure(Call<List<CommentDto>> call, Throwable t) {
            }
        });
        findViewById(R.id.post).setOnClickListener(view -> {
            Retrofit retrofit1 = MainBuilder.getInstance();
            CommentDto commentDto = new CommentDto();
            TextView comment1 = findViewById(R.id.add_comment);
            commentDto.setPostId(postId);
            commentDto.setUserEmail(userId);
            commentDto.setComment(comment1.getText().toString());
            //time
            Long unixTime = System.currentTimeMillis() / 1000L;
            commentDto.setTimeStamp(unixTime);

//            commentDto.setParentCommentId(null);
            Call<Void> comment = retrofit1.create(MainInterface.class).addComment(commentDto);
            comment.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "posted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }

    @Override
    public void onUserClick(CommentDto commentDto, View view, int position) {
    }
    }
