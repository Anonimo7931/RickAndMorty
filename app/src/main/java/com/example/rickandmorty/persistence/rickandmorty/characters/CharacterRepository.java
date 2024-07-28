package com.example.rickandmorty.persistence.rickandmorty.characters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.rickandmorty.domain.sqlite.characters.Character;
import com.example.rickandmorty.infrastructure.sqlite.SqlHelper;

import java.util.ArrayList;
import java.util.List;

public class CharacterRepository implements ICharacterRepository {
    private SQLiteDatabase database;
    private SqlHelper dbHelper;

    public CharacterRepository(Context context){
        dbHelper = new SqlHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            dbHelper.close();
        }
    }

    @Override
    public void addCharacter(Character character)
    {
        ContentValues values = new ContentValues();

        if(!existCharacter(character.idCharacter)){
            values.put("IdCharacter", character.idCharacter);
            values.put("Name", character.name);
            values.put("Image", character.image);
            values.put("Description", character.description);
            values.put("Created", character.created);

            database.insert("CHARACTERS", null, values);
        }
    }

    @Override
    public List<Character> getAllCharacters() {
        List<Character> characters = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(
                    "CHARACTERS",               // Table name
                    new String[]{"Id", "IdCharacter", "Name", "Image", "Created", "Description"}, // Columns to return
                    null,                  // Selection criteria (no filter)
                    null,                  // Selection args (no args)
                    null,                  // Group by
                    null,                  // Having
                    null                   // Order by
            );

            if (cursor == null || !cursor.moveToFirst()) {
                return characters;
            }

            do {
                Character character = new Character();

                character.id = cursor.getInt(0);
                character.idCharacter = cursor.getInt(1);
                character.name = cursor.getString(2);
                character.image = cursor.getString(3);
                character.created = cursor.getString(4);
                character.description = cursor.getString(5);

                characters.add(character);
            } while (cursor.moveToNext());

        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return characters;
    }

    private Boolean existCharacter(Integer IdCharacter) {
        String selection = "IdCharacter = ?";
        String[] selectionArgs = { String.valueOf(IdCharacter) };

        String[] projection = {
                "IdCharacter"
        };

        Cursor cursor = database.query(
                "CHARACTERS",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor == null){
            return false;
        }

        //exist the cursor?
        return (cursor.getCount() > 0);
    }

    @Override
    public void updateCharacter(Character character) {
        ContentValues values = new ContentValues();

        values.put("IdCharacter", character.idCharacter);
        values.put("Name", character.name);
        values.put("Image", character.image);
        values.put("Description", character.description);
        values.put("Created", character.created);

        String selection = "IdCharacter = ?";
        String[] selectionArgs = { String.valueOf(character.idCharacter) };

        database.update(
                "CHARACTERS",
                values,
                selection,
                selectionArgs
        );
    }

    @Override
    public void deleteCharacter(Character character) {
        String selection = "IdCharacter = ?";
        String[] selectionArgs = { String.valueOf(character.idCharacter) };

        database.delete(
                "CHARACTERS",
                selection,
                selectionArgs
        );
    }
}