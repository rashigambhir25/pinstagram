package com.example.pinstagram.Post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.Feed.Dashboard;
import com.example.pinstagram.Feed.FeedFragment;
import com.example.pinstagram.Feed.Story;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.R;

public class StoryUpload extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_upload);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Story story = (Story) bundle.getSerializable("story");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView storyimg = findViewById(R.id.story_image);
        VideoView storyvideo = findViewById(R.id.story_video);
        String mediaurl = story.getUrl();
//        String result = mediaurl.substring(mediaurl.lastIndexOf('.') + 1).trim();

        if(story.getType()){
            storyvideo.setVisibility(View.INVISIBLE);
            storyimg.setVisibility(View.VISIBLE);
            Glide.with(storyimg).load(mediaurl).into(storyimg);
        }
        else{
            storyimg.setVisibility(View.INVISIBLE);
//            MediaController mediaController = new MediaController(this);
//            mediaController.setMediaPlayer(storyvideo);
//            mediaController.setAnchorView(storyvideo);
            storyvideo.setVisibility(View.VISIBLE);
//            storyvideo.setMediaController(mediaController);
            storyvideo.requestFocus();
            storyvideo.setVideoURI(Uri.parse(mediaurl));
            storyvideo.setOnPreparedListener(mediaPlayer1 -> {
                storyvideo.requestFocus();
            });
            storyvideo.start();
//            storyvideo.setVideoURI(Uri.parse(mediaurl));
        }

        new Handler().postDelayed(() -> {
            Intent i=new Intent(StoryUpload.this, Dashboard.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
        findViewById(R.id.cancel_story).setOnClickListener(v -> {
            Intent i=new Intent(StoryUpload.this,
                    Dashboard.class);
            startActivity(i);
            finish();
        });
    }

}
