package com.example.movieapp;

import java.util.ArrayList;

public class User {
    private String email;
    public ArrayList<Movie> favorites = new ArrayList<Movie>();

    public boolean User(String email, String password) {
        //Check if user email exists in db if not create user else return false
        return false;
    }

    public void addMovie(String title, String poster_url) {
        if (!favorites.contains(new Movie(title, poster_url))) {
            favorites.add(new Movie(title, poster_url));
        }
    }

    public void removeMovie(Movie movie) {
        if(favorites.contains(movie)) {
            favorites.remove(movie);
        }
    }

}
