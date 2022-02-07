package com.example.pinstagram.Comments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinstagram.Post.StoryImage;
import com.example.pinstagram.Post.StoryVideo;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements CommentReplyAdapter.CommentReplyInterface {

    private final List<CommentDto> commentDtoList;
    private final Context context;
    private final CommentDtoInterface commentDtoInterface;
    final Dialog postMedia;

    public CommentAdapter(List<CommentDto> commentDtoList, Context context, CommentDtoInterface commentDtoInterface) {
        this.commentDtoList = commentDtoList;
        this.context = context;
        this.commentDtoInterface = commentDtoInterface;
        postMedia = new Dialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentitem1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CommentDto commentDto = commentDtoList.get(position);
        holder.comment.setText(commentDto.getComment()+"");
        holder.recyclerView.setVisibility(View.GONE);
//        String childComment=holder.childComment.getText().toString();
        if(commentDto.getChildCount()==0){
            holder.viewMore.setVisibility(View.GONE);
        }
        holder.reply.setOnClickListener(view -> {
            holder.viewMore.setVisibility(View.VISIBLE);
            Retrofit retrofit1 = MainBuilder.getInstance();
            CommentDto commentDto1 = new CommentDto();
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId","B");
//            String comment2 = holder.childComment.getText().toString();
//            Log.d("hey", comment2+ "");
            commentDto1.setPostId(commentDto.getPostId());
            commentDto1.setUserEmail(userId);
            commentDto1.setParentCommentId(commentDto.getId());
//            commentDto1.setComment(comment2.getText().toString());
            commentDto1.setComment("this feature will be added in next update");
            //Time
            Long unixTime = System.currentTimeMillis() / 1000L;
            commentDto1.setTimeStamp(unixTime);

            Call<Void> comments = retrofit1.create(MainInterface.class).addComment(commentDto1);
            comments.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context.getApplicationContext(), "posted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        });
//        holder.reply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////               postMedia.setTitle("Post");
//                postMedia.setContentView(R.layout.reply_option);
//                TextView replyText = postMedia.findViewById(R.id.textView6);
//                Button  replyBt= (Button)postMedia.findViewById(R.id.button_video);
//                Toast.makeText(view.getContext(), "reply", Toast.LENGTH_SHORT).show();
//                replyBt.setOnClickListener(view1 -> {
//                    holder.viewMore.setVisibility(View.VISIBLE);
//                    Retrofit retrofit1 = MainBuilder.getInstance();
//                    CommentDto commentDto1 = new CommentDto();
//                    SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
//                    String userId = sharedPreferences.getString("userId","B");
////            TextView comment2 = holder.comment.findViewById(R.id.add_comment);
//                    commentDto1.setPostId(commentDto.getPostId());
//                    commentDto1.setUserEmail(userId);
//                    commentDto1.setParentCommentId(commentDto.getId());
//                    commentDto1.setComment(replyText.getText().toString());
//                    Call<Void> comments = retrofit1.create(MainInterface.class).addComment(commentDto1);
//                    comments.enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            Toast.makeText(context.getApplicationContext(), "posted", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                });
//            }
//        });
//        holder.reply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                postMedia.setTitle("Post");
//                postMedia.setContentView(R.layout.reply_option);
//                Button reply = (Button)postMedia.findViewById(R.id.button_reply_text);
//                reply.setOnClickListener(view1 -> {
//
//                });
//                postMedia.show();
//            }
//        });
        holder.viewMore.setOnClickListener(view -> {
            holder.recyclerView.setVisibility(View.VISIBLE);
            Retrofit retrofit= MainBuilder.getInstance();
            Call<List<CommentDto>> childComments=retrofit.create(MainInterface.class).getCommentsByParentId(commentDto.getPostId(),commentDto.getId());
            childComments.enqueue(new Callback<List<CommentDto>>() {
                @Override
                public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                    RecyclerView recyclerView= holder.recyclerView.findViewById(R.id.recycle_card1);
                    CommentReplyAdapter commentReplyAdapter= new CommentReplyAdapter(response.body(), CommentAdapter.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(commentReplyAdapter);
                }
                @Override
                public void onFailure(Call<List<CommentDto>> call, Throwable t) {

                }
            });
        });
        holder.rootView.setOnClickListener((view -> {
            commentDtoInterface.onUserClick(commentDto,view,holder.getAdapterPosition());
        }));

    }

    @Override
    public int getItemCount() {
        return commentDtoList.size();
    }

    @Override
    public void onUserClick(CommentDto commentDto) {

    }

    public interface CommentDtoInterface{
        void onUserClick(CommentDto commentDto,View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView comment;
        private Button viewMore;
        private View rootView;
        private RecyclerView recyclerView;
        private Button reply;
        private EditText childComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            comment = itemView.findViewById(R.id.comment_parent);
            viewMore = itemView.findViewById(R.id.bt_view_more);
            recyclerView = itemView.findViewById(R.id.recycle_card1);
            reply = itemView.findViewById(R.id.bt_reply);
            childComment = itemView.findViewById(R.id.add_comment);
        }

    }
}
