package com.example.pinstagram.Feed;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.Post.ReactionDto;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InstagramFeedRVAdapter extends RecyclerView.Adapter<InstagramFeedRVAdapter.ViewHolder> {

    private final List<PostDto> postDtoList;
    private final InstaModalInterface instaModalInterface;
    private Context context;


    public InstagramFeedRVAdapter(List<PostDto> postDtoList, InstaModalInterface instaModalInterface, Context context) {
        this.postDtoList = postDtoList;
        this.instaModalInterface = instaModalInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insta_feed_rv_item, parent, false);
        return new InstagramFeedRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","B");
        PostDto postDto = postDtoList.get(position);
        holder.authorTV.setText(postDto.getUserId()+"");
        holder.likeTV.setText(postDto.getNumberOfLikes()+"");
        holder.desctv.setText(postDto.getDescription()+"");
        Glide.with(holder.postIV.getContext()).load(postDto.getUrl()).placeholder(R.drawable.sample).into(holder.postIV);
        //mere change
         holder.like.setOnClickListener(view -> {
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
                   Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onFailure(Call<Void> call, Throwable t) {

               }
           });
       });

       holder.dislike.setOnClickListener(view -> {
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
                   Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show();

               }
               @Override
               public void onFailure(Call<Void> call, Throwable t) {

               }
           });

       });

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
        private Button like;
        private Button dislike;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            authorIV = itemView.findViewById(R.id.idCVAuthor_feed);
            authorTV = itemView.findViewById(R.id.idTVAuthorName_feed);
            postIV = itemView.findViewById(R.id.idIVPost_feed);
            likeTV = itemView.findViewById(R.id.idTVLikes);
            desctv = itemView.findViewById(R.id.idTVPostDesc);
            like=itemView.findViewById(R.id.thumbsUp_feed);
            dislike=itemView.findViewById(R.id.thumbsDown_feed);
        }
    }
}
