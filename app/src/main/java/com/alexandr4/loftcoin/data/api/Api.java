package com.alexandr4.loftcoin.data.api;

import com.alexandr4.loftcoin.data.api.model.RateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    String CONVERT = "USD,EUR,RUB";

    @GET("cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: 4cb78b8c-5e18-4ed9-8945-766bd4259f2e")
    Call<RateResponse> rates(@Query("convert") String convert);
}
