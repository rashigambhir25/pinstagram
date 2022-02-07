package com.example.pinstagram.Recommendation;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.pinstagram.Ads.CategoryAddsDto;
import com.example.pinstagram.MyProfile.PostDto;
import com.example.pinstagram.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter1 extends BaseAdapter {

    private List<CategoryAddsDto> categoryAddsDtos;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter1(List<CategoryAddsDto> categoryAddsDtos, Context context) {
        this.categoryAddsDtos = categoryAddsDtos;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryAddsDtos.size();
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
//        imageView.setImageResource(imagePhotos[i]);

//        ImageView imageView=view.findViewById(R.id.imageView);
//        VideoView video = view.findViewById(R.id.video_post);
//        Glide.with(view.findViewById(R.id.imageView).getContext()).load(posts.get(i).getUrl()).placeholder(R.drawable.sample);
//        tvName.setText(imageNames[i]);
//        imageView.setImageResource(imagePhotos[i]);
//        if(posts.get(i).getType()){
        Picasso.get().load(categoryAddsDtos.get(i).getUrl()).placeholder(R.drawable.sample).fit().into(imageView);
        return view;
    }
}
