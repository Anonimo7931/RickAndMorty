package com.example.rickandmorty.infrastructure.apiservice;

import com.example.rickandmorty.domain.characters.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService
{
    @GET("api/character")
    Call<CharacterResponse> getCharacter();

    @GET("api/character")
    Call<CharacterResponse> getSpecificPageCharacter(@Query("page") int pageId);
}
