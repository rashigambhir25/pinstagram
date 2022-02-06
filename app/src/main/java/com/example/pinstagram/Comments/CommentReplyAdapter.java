package com.example.pinstagram.Comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinstagram.R;

import java.util.List;


public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.ViewHolder> {

    private final List<CommentDto> commentDtoList;
    private final CommentReplyInterface commentReplyInterface;

    public CommentReplyAdapter(List<CommentDto> commentDtoList, CommentReplyInterface commentReplyInterface) {
        this.commentDtoList = commentDtoList;
        this.commentReplyInterface = commentReplyInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentitem2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentDto commentDto = commentDtoList.get(position);
        holder.childComment.setText(commentDto.getComment()+"");
    }

    @Override
    public int getItemCount() {
        return commentDtoList.size();
    }

    public interface CommentReplyInterface{
        void onUserClick(CommentDto commentDto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView childComment;
        private View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            childComment = itemView.findViewById(R.id.tv_child_comment);

        }
    }
}
