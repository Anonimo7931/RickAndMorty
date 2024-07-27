package com.example.rickandmorty.domain.characters;

import com.google.gson.annotations.SerializedName;

public class Information {
    @SerializedName("count")
    public Integer count;
    @SerializedName("pages")
    public Integer pages;
    @SerializedName("next")
    public String next;
    @SerializedName("prev")
    public String prev;
}
