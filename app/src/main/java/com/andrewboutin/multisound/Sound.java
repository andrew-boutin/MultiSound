package com.andrewboutin.multisound;

/**
 * Created by Andrew on 8/9/2015.
 *
 * Holds all informations that makes up a sound.
 */
public class Sound {
    private long id; // db id
    private String name; // name given by user
    private String fileName; // name of the file

    /**
     * Creates a new sound and stores all of its member variables.
     *
     * @param name Name the user gave the sound.
     * @param fileName Name of the sound file.
     * @param id db id for the record.
     */
    public Sound(String name, String fileName, long id){
        this.name = name;
        this.fileName = fileName;
        this.id = id;
    }

    /**
     * Sets the name of the sound.
     *
     * @param name Sound name.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retrieves the name of the sound.
     *
     * @return Name of the sound.
     */
    public String getName(){
        return name;
    }

    /**
     * Changes the name of the sound file.
     *
     * @param fileName Name of the sound file.
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
     * Retrieves the name of the sound file.
     *
     * @return Name of the sound file.
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * Retrieves the database id for the sound record.
     *
     * @return database id for the sound record.
     */
    public long getId(){
        return id;
    }
}
