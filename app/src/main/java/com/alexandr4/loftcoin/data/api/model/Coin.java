package com.alexandr4.loftcoin.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Coin {

    @SerializedName("website_slug")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("symbol")
    public String symbol;

    @SerializedName("slug")
    public String slug;

    @SerializedName("last_updated")
    public String lastUpdate;

    public Map<String, Quote> quotes;
}
