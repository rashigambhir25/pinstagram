package com.example.pinstagram.RetrofitMain;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainBuilder {
    public MainBuilder() {
    }
    private static Retrofit instance;
    public static Retrofit getInstance(){
        if (instance ==null){
            synchronized (MainBuilder.class){
                if(instance==null){
                    instance = new Retrofit.Builder().baseUrl("http://10.177.1.207:9000").addConverterFactory(ScalarsConverterFactory.create()).
                            addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient()).build();
                }
            }
        }
        return instance;
    }
}
