package com.example.rickandmorty.application.rickandmortyusecases.getcharacters;

import android.util.Log;

import com.example.rickandmorty.domain.characters.CharacterResponse;
import com.example.rickandmorty.infrastructure.apiservice.ApiService;
import com.example.rickandmorty.infrastructure.httpclient.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCharactersUseCase implements IGetCharactersUseCase
{
    @Override
    public String execute()
    {
        ApiService apiInterface;

        apiInterface = HttpClient.getClient().create(ApiService.class);

        Call<CharacterResponse> call = apiInterface.getCharacter();

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                 CharacterResponse resource = response.body();


            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
             call.cancel();
            }
        }

        );


        return "";
    }
}
