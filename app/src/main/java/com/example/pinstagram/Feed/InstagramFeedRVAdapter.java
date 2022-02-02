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
import com.example.pinstagram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InstagramFeedRVAdapter extends RecyclerView.Adapter<InstagramFeedRVAdapter.ViewHolder> {

    private final List<InstaModel> instaModalArrayList;
    private final InstaModalInterface instaModalInterface;

    public InstagramFeedRVAdapter(List<InstaModel> instaModalArrayList, InstaModalInterface instaModalInterface) {
        this.instaModalArrayList = instaModalArrayList;
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
        InstaModel instaModel = instaModalArrayList.get(position);
        holder.authorTV.setText(instaModel.getUsername()+"");
        holder.likeTV.setText(instaModel.getLikesCount()+"");
        holder.desctv.setText(instaModel.getCaption()+"");
        Glide.with(holder.postIV.getContext()).load(instaModel.getMedia_url()).placeholder(R.drawable.sample).into(holder.postIV);
//        if (instaModel.getMedia_type().equals("IMAGE")) {
//            Picasso.get().load(instaModel.getMedia_url()).into(holder.postIV);
//        }
        Picasso.get().load(instaModel.getAuthor_url()).into(holder.authorIV);
        holder.rootView.setOnClickListener((view -> {
            instaModalInterface.onUserClick(instaModel,view,holder.getAdapterPosition());
        }));

    }

    @Override
    public int getItemCount() {
        return instaModalArrayList.size();
    }
    public interface InstaModalInterface
    {
        void onUserClick(InstaModel instaModel,View v,int position);
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
            authorIV = itemView.findViewById(R.id.idCVAuthor);
            authorTV = itemView.findViewById(R.id.idTVAuthorName);
            postIV = itemView.findViewById(R.id.idIVPost);
            likeTV = itemView.findViewById(R.id.idTVLikes);
            desctv = itemView.findViewById(R.id.idTVPostDesc);
        }
    }
}
