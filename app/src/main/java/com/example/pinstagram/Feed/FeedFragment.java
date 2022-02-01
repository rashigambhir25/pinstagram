package com.example.pinstagram.Feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pinstagram.Post.DashBoardForPostFragment;
import com.example.pinstagram.Post.PostFragment;
import com.example.pinstagram.R;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment implements InstagramFeedRVAdapter.InstaModalInterface {

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<InstaModel> instaModelList = new ArrayList<>();
        generateUserData(instaModelList);
        RecyclerView recyclerView = getView().findViewById(R.id.idRVInstaFeeds);
        InstagramFeedRVAdapter instagramFeedRVAdapter = new InstagramFeedRVAdapter(instaModelList, FeedFragment.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(instagramFeedRVAdapter);
//       view.findViewById(R.id.view_post).setOnClickListener(view1 -> {
//            startActivity(new Intent(, DashBoardForPostFragment.class));
//        });
//        Intent intent = new Intent(getActivity(),DashBoardForPostFragment.class);
//        intent.putExtra("Hello","Nisar");
//        getActivity().startActivity(intent);
//        view.findViewById(R.id.view_post).setOnClickListener(view1 -> {
//            startActivity(new Intent(, DashBoardForPostFragment.class));
//            Intent intent = new Intent(getActivity(),DashBoardForPostFragment.class);
//            intent.putExtra("Hello","Nisar");
//            getActivity().startActivity(intent);
//
//        });

    }

//    private void setCurrentFragment() {
//       startActivity(new Intent(getContext(), DashBoardForPostFragment.class));
//        Intent intent = new Intent(getActivity(),DashBoardForPostFragment.class);
//        intent.putExtra("Hello","Nisar");
//        getActivity().startActivity(intent);
//    }

    public void generateUserData(List<InstaModel> instaModels) {
//        user image, username, post image, likes count,caption
//                productDataList.add(new ProductData("Employee 1", 100,"https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png"));
        instaModels.add(new InstaModel("1", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME1", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 100));
        instaModels.add(new InstaModel("2", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME2", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 101));
        instaModels.add(new InstaModel("3", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME3", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 103));
        instaModels.add(new InstaModel("4", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME4", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 104));
        instaModels.add(new InstaModel("5", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME5", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 105));
        instaModels.add(new InstaModel("6", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME6", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 106));
        instaModels.add(new InstaModel("7", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME7", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 107));
        instaModels.add(new InstaModel("8", "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
                "USERNAME8", "caption", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 108));
    }

    @Override
    public void onUserClick(InstaModel instaModel) {
        getView().findViewById(R.id.view_post).setOnClickListener(view1 -> {
//          startActivity(new Intent(, DashBoardForPostFragment.class));
            Intent intent = new Intent(getActivity(), DashBoardForPostFragment.class);
//            intent.putExtra("Hello", "Nisar");
            getActivity().startActivity(intent);

        });
    }
}