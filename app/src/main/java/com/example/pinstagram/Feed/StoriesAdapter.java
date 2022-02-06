package com.example.pinstagram.Feed;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {


    private final List<Story> storyDataList;
    private final StoryDataInterface storyDataInterface;

    public StoriesAdapter(List<Story> storyDataList, StoryDataInterface storyDataInterface) {
        this.storyDataList = storyDataList;
        this.storyDataInterface = storyDataInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story storyData = storyDataList.get(position);
//        if(storyData.getType()){
//            holder.storyImage.setVisibility(View.VISIBLE);
//            holder.storyVideo.setVisibility(View.INVISIBLE);
//            Picasso.get().load(storyData.getUrl()).placeholder(R.drawable.sample).fit().into(holder.storyImage);
//        }
//        else{
//            holder.storyImage.setVisibility(View.INVISIBLE);
//            holder.storyVideo.setVisibility(View.VISIBLE);
//            holder.storyVideo.requestFocus();
//            holder.storyVideo.setVideoURI(Uri.parse(storyData.getUrl()));
//            holder.storyVideo.setOnPreparedListener(mediaPlayer1 -> {
//                holder.storyVideo.requestFocus();
//            });
//            holder.storyVideo.start();
//
//        }
        holder.userName.setText(storyData.getUserId());
        Picasso.get().load(storyData.getUrl()).placeholder(R.drawable.sample).fit().into(holder.userPic);

        holder.rootView.setOnClickListener((view -> storyDataInterface.onStoryClick(storyData)));

    }

    @Override
    public int getItemCount() {
        return storyDataList.size();
    }


    public interface StoryDataInterface {
        void onStoryClick(Story story);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView userPic;
        private final TextView userName;
//        private final VideoView storyVideo;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            userPic = view.findViewById(R.id.user_story_pic);
            userName = view.findViewById(R.id.user_story_name);
        }

    }
}
