package com.example.pinstagram.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InstagramFeedRVAdapter extends RecyclerView.Adapter<InstagramFeedRVAdapter.ViewHolder> {

    private final List<PostDto> postDtoList;
    private final InstaModalInterface instaModalInterface;

    public InstagramFeedRVAdapter(List<PostDto> postDtoList, InstaModalInterface instaModalInterface) {
        this.postDtoList = postDtoList;
        this.instaModalInterface = instaModalInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_feed_rv_item, parent, false);
        return new InstagramFeedRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        InstaModel modal = instaModalArrayList.get(position);
//        holder.authorTV.setText(modal.getUsername());
//        if (modal.getMedia_type().equals("IMAGE")) {
//            Picasso.get().load(modal.getMedia_url()).into(holder.postIV);
//        }
//        holder.desctv.setText(modal.getCaption());
//        holder.likeTV.setText("" + modal.getLikesCount() + " likes");
//        Picasso.get().load(modal.getAuthor_url()).into(holder.authorIV);
        PostDto postDto = postDtoList.get(position);
        holder.authorTV.setText(postDto.getUserId()+"");
        holder.likeTV.setText(postDto.getNumberOfLikes()+"");
        holder.desctv.setText(postDto.getDescription()+"");
        Glide.with(holder.postIV.getContext()).load(postDto.getUrl()).placeholder(R.drawable.sample).into(holder.postIV);
//        if (instaModel.getMedia_type().equals("IMAGE")) {
//            Picasso.get().load(instaModel.getMedia_url()).into(holder.postIV);
//        }
        Picasso.get().load(postDto.getUrl()).into(holder.authorIV);
        holder.rootView.setOnClickListener((view -> {
            instaModalInterface.onUserClick(postDto,view,holder.getAdapterPosition());
        }));

    }

    @Override
    public int getItemCount() {
        return postDtoList.size();
    }
    public interface InstaModalInterface
    {
        void onUserClick(PostDto postDto,View v,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView authorIV;
        private TextView authorTV;
        private ImageView postIV;
        private TextView likeTV, desctv;
        private View rootView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            authorIV = itemView.findViewById(R.id.idCVAuthor_feed);
            authorTV = itemView.findViewById(R.id.idTVAuthorName_feed);
            postIV = itemView.findViewById(R.id.idIVPost_feed);
            likeTV = itemView.findViewById(R.id.idTVLikes);
            desctv = itemView.findViewById(R.id.idTVPostDesc);
        }
    }
}
