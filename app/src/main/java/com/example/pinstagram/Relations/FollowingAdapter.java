package com.example.pinstagram.Relations;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private final List<String> followingModelList;
    private final Context context;
    private final FollowingListInterface followingListInterface;

    public FollowingAdapter(List<String> followingModelList, Context context, FollowingListInterface followingListInterface) {
        this.followingModelList = followingModelList;
        this.context = context;
        this.followingListInterface = followingListInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String followingModel = followingModelList.get(position);
        holder.userName.setText(followingModel);
        holder.following.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.pinstagram",context.MODE_PRIVATE);
            Retrofit retrofit = MainBuilder.getInstance();
            Call<Void> following = retrofit.create(MainInterface.class).deleteConnection(followingModel,sharedPreferences.getString("userId","B"));
            following.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(view.getContext(), "Unfollowed!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
        holder.rootView.setOnClickListener((view -> followingListInterface.onUserClick(followingModel)));

    }

    @Override
    public int getItemCount() {
        return followingModelList.size();
    }

    public interface FollowingListInterface{
        void onUserClick(String followersModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final Button following;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            following = view.findViewById(R.id.following);
            userName = view.findViewById(R.id.following_username);
        }

    }
}
