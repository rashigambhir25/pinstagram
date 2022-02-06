package com.example.pinstagram.Recommendation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pinstagram.Post.Post;
import com.example.pinstagram.R;

import java.util.ArrayList;

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
        GridView gridView;
        String[] names={"test name","image","image1","image2"};
        int[] images={R.drawable.sample,R.drawable.sample,R.drawable.sample,R.drawable.sample};
            gridView=getView().findViewById(R.id.grid_view);
            CustomAdapter1 customAdapter=new CustomAdapter1(names,images,getContext());
            gridView.setAdapter(customAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedName=names[i];
                    int selectedImage=images[i];
                    startActivity(new Intent(getContext(),Post.class).putExtra("name",selectedName).putExtra("image",selectedImage));
                }
            });
    }
}