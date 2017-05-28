package com.example.alber.prueba10.clases;

/**
 * Created by alber on 28/05/2017.
 */

public class MangaImagen {

    private int id;
    private String movieName;
    private String imageLink;

    public MangaImagen(int id, String movieName, String imageLink) {
        this.id = id;
        this.movieName = movieName;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
