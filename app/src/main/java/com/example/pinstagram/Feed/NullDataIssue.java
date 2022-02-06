package com.example.pinstagram.Feed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.R;

public class NullDataIssue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_data_issue);
        ImageView gifView = findViewById(R.id.iv_gif);
        Glide.with(this).asGif().load("https://c.tenor.com/_OtyZNwfShYAAAAC/instagram-logo.gif")
                .into(gifView);

    }
}