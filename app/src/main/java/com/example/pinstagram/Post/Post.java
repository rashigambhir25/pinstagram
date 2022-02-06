package com.example.pinstagram.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.pinstagram.Comments.Comments;
import com.example.pinstagram.Feed.InstaModel;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Post extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        PostDto postDto = (PostDto) bundle.getSerializable("post");

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");

        TextView username = findViewById(R.id.idTVAuthorName);
        TextView caption = findViewById(R.id.idTVPostDesc_p);
        ImageView image = findViewById(R.id.idIVPost_p);
        VideoView video = findViewById(R.id.video_post);
        TextView likes = findViewById(R.id.idTVLikes_p);
        username.setText(postDto.getUserId());
        caption.setText(postDto.getDescription());
        likes.setText(postDto.getNumberOfLikes()+"");
        if(postDto.getType()){
            image.setVisibility(View.VISIBLE);
            video.setVisibility(View.INVISIBLE);
        Picasso.get().load(postDto.getUrl()).placeholder(R.drawable.sample).fit().into(image);}
        else
        {
//            MediaController mediaController = new MediaController(this);
//            mediaController.setMediaPlayer(video);
//            mediaController.setAnchorView(video);
            video.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);
//            video.setMediaController(mediaController);
            video.requestFocus();
            video.setVideoURI(Uri.parse(postDto.getUrl()));
            video.setOnPreparedListener(mediaPlayer1 -> {
                video.requestFocus();
            });
            video.start();

        }
        findViewById(R.id.button_thumbs_up).setOnClickListener(view -> {
            ReactionDto reactionDto=new ReactionDto();
            reactionDto.setPostId(postDto.getId());
            reactionDto.setReactionBy(userId);
            reactionDto.setReactionType(true);
            //Time
            Long unixTime = System.currentTimeMillis() / 1000L;
            reactionDto.setTimeStamp(unixTime);

            Retrofit retrofit= MainBuilder.getInstance();
            Call<Void> posReaction=retrofit.create(MainInterface.class).addReaction(reactionDto);
            posReaction.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "Liked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        findViewById(R.id.button_thumbs_down).setOnClickListener(view -> {
            ReactionDto reactionDto=new ReactionDto();
            reactionDto.setPostId(postDto.getId());
            reactionDto.setReactionBy(userId);
            reactionDto.setReactionType(false);
            //Time
            Long unixTime = System.currentTimeMillis()/1000L;
            reactionDto.setTimeStamp(unixTime);

            Retrofit retrofit= MainBuilder.getInstance();
            Call<Void> negReaction=retrofit.create(MainInterface.class).addReaction(reactionDto);
            negReaction.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getApplicationContext(), "Disliked", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

        });

        findViewById(R.id.comments_p).setOnClickListener(view -> {
            Intent i = new Intent(this,Comments.class);
            Long postId = postDto.getId();
            i.putExtra("postId",postId);
            startActivity(i);
        });
    }

}