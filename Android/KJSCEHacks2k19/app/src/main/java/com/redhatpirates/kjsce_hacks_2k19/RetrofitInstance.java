package com.redhatpirates.kjsce_hacks_2k19;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit instance = null;

    public static String BASE_URL = "https://red-hat-pirates.herokuapp.com/api/";

    public static Retrofit getInstance(){

        if(instance == null){
            instance = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return instance;
    }


}
