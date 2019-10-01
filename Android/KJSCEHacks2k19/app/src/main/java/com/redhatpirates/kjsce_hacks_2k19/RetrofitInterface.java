package com.redhatpirates.kjsce_hacks_2k19;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    //Todo-add methods
    @POST("sign-up/")
    Call<UserDetails> signIn(@Body UserDetails userDetails);
}
