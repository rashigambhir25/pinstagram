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

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private final List<String> followersModelList;
    private final Context context;
    private final FollowersListInterface followersListInterface;

    public FollowersAdapter(List<String> followersModelList, Context context, FollowersListInterface followersListInterface) {
        this.followersModelList = followersModelList;
        this.context = context;
        this.followersListInterface = followersListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String followersModel = followersModelList.get(position);
        holder.userName.setText(followersModel);
        holder.remove.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.pinstagram",context.MODE_PRIVATE);
            Retrofit retrofit = MainBuilder.getInstance();
            Call<Void> following =retrofit.create(MainInterface.class).deleteConnection(followersModel,sharedPreferences.getString("userId","B"));
            following.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            followersModelList.remove(position);
            notifyDataSetChanged();
        });
        holder.rootView.setOnClickListener((view -> followersListInterface.onUserClick(followersModel)));

    }

    @Override
    public int getItemCount() {
        return followersModelList.size();
    }

    public interface FollowersListInterface{
        void onUserClick(String followersModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final Button remove;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            remove = view.findViewById(R.id.button_remove);
            userName = view.findViewById(R.id.followers_username);
        }

    }
}
