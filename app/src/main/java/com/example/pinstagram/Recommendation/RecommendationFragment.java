package com.example.pinstagram.Recommendation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pinstagram.Ads.UserAdUrl;
import com.example.pinstagram.MyProfile.CustomAdapter;
import com.example.pinstagram.Post.Post;
import com.example.pinstagram.R;
import com.example.pinstagram.RetrofitMain.AddBuilder;
import com.example.pinstagram.RetrofitMain.MainBuilder;
import com.example.pinstagram.RetrofitMain.MainInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendationFragment extends Fragment {

    public RecommendationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        GridView gridView;
//        String[] names={"test name","image","image1","image2"};
//        int[] images={R.drawable.sample,R.drawable.sample,R.drawable.sample,R.drawable.sample};
//            gridView=getView().findViewById(R.id.grid_view);
//            CustomAdapter1 customAdapter=new CustomAdapter1(names,images,getContext());
//            gridView.setAdapter(customAdapter);
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String selectedName=names[i];
//                    int selectedImage=images[i];
////                    startActivity(new Intent(getContext(),Post.class).putExtra("name",selectedName).putExtra("image",selectedImage));
//                }
//            });
        Retrofit retrofit = AddBuilder.getInstance();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.pinstagram", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","rashi");
        Call<UserAdUrl> getAds = retrofit.create(MainInterface.class).getAds(userId);
        getAds.enqueue(new Callback<UserAdUrl>() {
            @Override
            public void onResponse(Call<UserAdUrl> call, Response<UserAdUrl> response) {
                if(response.code()==200||response.code()==201){
                    GridView gridView;
                    gridView = getView().findViewById(R.id.grid_view_recommendation);
                    CustomAdapter1 customAdapter = new CustomAdapter1(response.body().getCategoryAddsDto(),getContext());
                    gridView.setAdapter(customAdapter);
                }
                else{
                    Toast.makeText(getContext(), "No Ads for you", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAdUrl> call, Throwable t) {

            }
        });
    }
}