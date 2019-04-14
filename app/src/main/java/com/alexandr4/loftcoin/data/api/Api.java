package com.alexandr4.loftcoin.data.api;

import com.alexandr4.loftcoin.data.api.model.RateResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    String CONVERT = "USD";

    @GET("cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: 7a34e449-9f35-4c49-8628-d0fda0078fef")
    Observable<RateResponse> rates(@Query("convert") String convert);
}
