package com.example.rickandmorty.persistence.rickandmorty.characters;

import com.example.rickandmorty.domain.sqlite.characters.Character;

import java.util.List;

public interface ICharacterRepository {
    public void open();
    public void close();
    public void addCharacter(Character character);
    public List<Character> getAllCharacters();
    public void updateCharacter(Character character);
    public void deleteCharacter(Character character);
}
