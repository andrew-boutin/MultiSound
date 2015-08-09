package com.andrewboutin.multisound;

/**
 * Created by Andrew on 8/9/2015.
 */
public class Sound {
    private long id;
    private String name;
    private String fileName;

    public Sound(String name, String fileName, long id){
        this.name = name;
        this.fileName = fileName;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getFileName(){
        return fileName;
    }

    public long getId(){
        return id;
    }
}
