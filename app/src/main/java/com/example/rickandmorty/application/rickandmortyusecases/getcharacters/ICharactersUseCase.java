package com.example.rickandmorty.application.rickandmortyusecases.getcharacters;

import android.content.Context;

import com.example.rickandmorty.domain.sqlite.characters.Character;

import java.util.List;

public interface ICharactersUseCase
{
    public void executeSetCharacter(Context context);
    public List<Character> executeGetCharacters(Context context);
    public void executeUpdateCharacter(Context context, Character character);
    public void executeDeleteCharacter(Context context, Character character);
}
