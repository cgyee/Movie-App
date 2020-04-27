package com.example.movieapp;

public class Movie {
    private String title;
    private String poster_url;

    public Movie(String title, String poster_url) {
        this.title =title;
        this.poster_url =poster_url;
    }

    public String getTitle() {return title;}
    public String getPoster_url() {return poster_url;}
}
