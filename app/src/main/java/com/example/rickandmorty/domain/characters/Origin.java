package com.example.rickandmorty.domain.characters;

import com.google.gson.annotations.SerializedName;

public class Origin {
    @SerializedName("name")
    public String name;
    @SerializedName("url")
    public String url;
}
