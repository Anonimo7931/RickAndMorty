package com.example.rickandmorty.domain.characters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {
    @SerializedName("info")
    public Information information;
    @SerializedName("results")
    public List<CharacterInformation> charactersInformation;
}
