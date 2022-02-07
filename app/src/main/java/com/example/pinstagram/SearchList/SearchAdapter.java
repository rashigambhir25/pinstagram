package com.example.pinstagram.SearchList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinstagram.R;
import com.example.pinstagram.Relations.FollowingAdapter;
import com.example.pinstagram.UserProfile.UserDto;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private final List<UserDto> userDtoList;
    private final Context context;
    private final SearchInterface searchInterface;

    public SearchAdapter(List<UserDto> userDtoList, Context context, SearchInterface searchInterface) {
        this.userDtoList = userDtoList;
        this.context = context;
        this.searchInterface = searchInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDto userDto = userDtoList.get(position);
        String userName=userDto.getName();
        holder.userName.setText(userName);
        holder.rootView.setOnClickListener((view -> searchInterface.onUserClick(userDto,view, holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return userDtoList.size();
    }

    public interface SearchInterface{
        void onUserClick(UserDto userDto,View view,int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            userName = view.findViewById(R.id.username_search);
        }

    }
}
