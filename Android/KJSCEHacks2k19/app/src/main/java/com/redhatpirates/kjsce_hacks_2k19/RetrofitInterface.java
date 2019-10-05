package com.redhatpirates.kjsce_hacks_2k19;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @POST("sign-up/")
    Call<UserDetails> signIn(@Body UserDetails userDetails);
    @POST("get-expenditures/")
    Call<ArrayList<MoneySpent>> getExpenditures(@Body MoneySpent moneySpent);
    @POST("add-expenditure/")
    Call<MoneySpent>addExpenditure(@Body MoneySpent moneySpent);
}
