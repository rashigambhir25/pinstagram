package com.example.pinstagram.MyProfile;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

//    private String[] imageNames;
//    private int[ ] imagePhotos;
    private List<PostDto> posts;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<PostDto> posts, Context context) {
        this.posts = posts;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

    }
    //    public CustomAdapter(String[] imageNames, int[] imagePhotos, Context context) {
//        this.imageNames = imageNames;
//        this.imagePhotos = imagePhotos;
//        this.context = context;
//        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//    }

    @Override
    public int getCount() {
//        return imagePhotos.length ;
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=layoutInflater.inflate(R.layout.card_item,viewGroup,false);
        }
        ImageView imageView=view.findViewById(R.id.imageView);
        VideoView video = view.findViewById(R.id.video_post);
//        Glide.with(view.findViewById(R.id.imageView).getContext()).load(posts.get(i).getUrl()).placeholder(R.drawable.sample);
//        tvName.setText(imageNames[i]);
//        imageView.setImageResource(imagePhotos[i]);
//        if(posts.get(i).getType()){
        Picasso.get().load(posts.get(i).getUrl()).placeholder(R.drawable.sample).fit().into(imageView);
//    }

//        else {
//            MediaController mediaController = new MediaController(context);
//            mediaController.setMediaPlayer(video);
//            mediaController.setAnchorView(video);
////            video.setVisibility(View.VISIBLE);
//            video.setMediaController(mediaController);
//            video.requestFocus();
//            video.setVideoURI(Uri.parse(posts.get(i).getUrl()));
//            video.setOnPreparedListener(mediaPlayer1 -> {
//                video.requestFocus();
//            });
//            video.start();
//        }
        return view;
    }
}
