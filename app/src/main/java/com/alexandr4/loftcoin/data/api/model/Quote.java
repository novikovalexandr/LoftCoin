package com.alexandr4.loftcoin.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("price")
    public Double price;

    @SerializedName("percent_change_1h")
    public float percentChange1h;

    @SerializedName("persent_change_24h")
    public float percentChange24h;

    @SerializedName("percent_change_7d")
    public float percentChange7d;

}
