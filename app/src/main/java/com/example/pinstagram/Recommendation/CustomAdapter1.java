package com.example.pinstagram.Recommendation;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.R;

import java.util.List;

public class CustomAdapter1 extends BaseAdapter {

    private String[] imageNames;
    private int[ ] imagePhotos;
    private Context context;
    private LayoutInflater layoutInflater;
    public CustomAdapter1(String[] imageNames, int[] imagePhotos, Context context) {
        this.imageNames = imageNames;
        this.imagePhotos = imagePhotos;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return imagePhotos.length ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=layoutInflater.inflate(R.layout.card_item,viewGroup,false);
        }

        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(imagePhotos[i]);
        return view;
    }
}
