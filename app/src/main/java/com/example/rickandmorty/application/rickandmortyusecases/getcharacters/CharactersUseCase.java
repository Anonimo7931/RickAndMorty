package com.example.rickandmorty.application.rickandmortyusecases.getcharacters;

import android.content.Context;

import com.example.rickandmorty.domain.characters.CharacterResponse;
import com.example.rickandmorty.domain.sqlite.characters.Character;
import com.example.rickandmorty.infrastructure.apiservice.ApiService;
import com.example.rickandmorty.infrastructure.httpclient.HttpClient;
import com.example.rickandmorty.persistence.rickandmorty.characters.CharacterRepository;
import com.example.rickandmorty.persistence.rickandmorty.characters.ICharacterRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersUseCase implements ICharactersUseCase
{
    @Override
    public void executeSetCharacter(Context context)
    {
        ApiService apiInterface;

        apiInterface = HttpClient.getClient().create(ApiService.class);

        ICharacterRepository characterRepository = new CharacterRepository(context);
        characterRepository.open();

        Call<CharacterResponse> call = apiInterface.getCharacter();

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                CharacterResponse resource = response.body();

                Character character = new Character();

                if(!response.isSuccessful() || resource == null)
                {
                    return;
                }

                for (int i = 0; i < resource.charactersInformation.size(); i++)
                {
                    character.idCharacter = resource.charactersInformation.get(i).id;
                    character.name = resource.charactersInformation.get(i).name;
                    character.created = resource.charactersInformation.get(i).created;
                    character.image = resource.charactersInformation.get(i).image;
                    character.description = "";

                    characterRepository.addCharacter(character);
                }

                characterRepository.close();
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                call.cancel();
                characterRepository.close();
            }
        }

        );
    }

    @Override
    public List<Character> executeGetCharacters(Context context) {
        ICharacterRepository characterRepository = new CharacterRepository(context);
        characterRepository.open();

        List<Character> Characters = characterRepository.getAllCharacters();

        characterRepository.close();

        return Characters;
    }

    @Override
    public void executeUpdateCharacter(Context context, Character character) {
        ICharacterRepository characterRepository = new CharacterRepository(context);
        characterRepository.open();

        characterRepository.updateCharacter(character);

        characterRepository.close();
    }

    @Override
    public void executeDeleteCharacter(Context context, Character character) {
        ICharacterRepository characterRepository = new CharacterRepository(context);
        characterRepository.open();

        characterRepository.deleteCharacter(character);

        characterRepository.close();
    }
}
