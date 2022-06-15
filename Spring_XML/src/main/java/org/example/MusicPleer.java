package org.example;

import java.util.ArrayList;
import java.util.List;

public class MusicPleer {
    private List<Music> genre = new ArrayList<>();

    public List<Music> getGenre() {
        return genre;
    }

    public void setGenre(List<Music> genre) {
        this.genre = genre;
    }

    private String name;

    private int volume;



    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }



    public MusicPleer() {
    }


    public void playMusic() {

        for (Music m : genre) {
            System.out.println("Playing: " + m.getSong());
        }
    }
}
