package com.example.pinstagram.Feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.pinstagram.MyProfile.MyProfileFragment;
import com.example.pinstagram.Post.PostFragment;
import com.example.pinstagram.R;
import com.example.pinstagram.Recommendation.RecommendationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_dashboard);
        FeedFragment feedFragment = new FeedFragment();
        RecommendationFragment recommendationFragment = new RecommendationFragment();
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        setCurrentFragment(feedFragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.recommendation:
                    setCurrentFragment(recommendationFragment);
                    break;

                case R.id.profile:
                    setCurrentFragment(myProfileFragment);
                    break;

                default:
                    setCurrentFragment(feedFragment);
                    break;
            }
            return true;
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
    }

