package com.andrewboutin.multisound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

// TODO: upgrade and something else?

/**
 * Created by Andrew on 8/9/2015.
 */
public class DBHandler {
    private static DBHandler dbHandler;
    private SQLiteDatabase db;

    public static final String TABLE_SOUNDS = "sounds";
    public static final String KEY_ID = "id";

    public static final String SOUND_NAME = "sound_name";
    public static final String FILE_NAME = "file_name";

    private static final String CREATE_TABLE_SOUND = "CREATE TABLE IF NOT EXISTS " + TABLE_SOUNDS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + SOUND_NAME + " TEXT,"
            + FILE_NAME + " TEXT" + ")";

    private DBHandler(Context context){
        db = context.openOrCreateDatabase("DATABASE", android.content.Context.MODE_PRIVATE, null);

        db.execSQL(CREATE_TABLE_SOUND);
    }

    public static DBHandler getDbHandler(Context context){
        if(dbHandler == null)
            dbHandler = new DBHandler(context);

        return dbHandler;
    }

    public Sound createSound(String soundName, String fileName){
        ContentValues values = new ContentValues();
        values.put(SOUND_NAME, soundName);
        values.put(FILE_NAME, fileName);

        long id = db.insert(TABLE_SOUNDS, null, values);

        return new Sound(soundName, fileName, id);
    }

    public void deleteSound(Sound sound){
        long id = sound.getId();
        db.delete(TABLE_SOUNDS, KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Sound> getAllSounds() {
        ArrayList<Sound> sounds = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SOUNDS;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                String name = c.getString(c.getColumnIndex(SOUND_NAME));
                String file = c.getString(c.getColumnIndex(FILE_NAME));
                long id = c.getLong(c.getColumnIndex(KEY_ID));

                Sound sound = new Sound(name, file, id);

                sounds.add(sound);
            } while (c.moveToNext());
        }

        return sounds;
    }
}
