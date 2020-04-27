package com.example.movieapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class User {
    private String email;
    private ArrayList<Movie> favorites = new ArrayList<Movie>();

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

    public void saveFavorites() {
        try {
            ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream("favorites.dat")));
            ArrayList<Movie> output = favorites;
            out.writeObject(output);
            out.close();
        }
        catch (SecurityException | IOException e) {
            System.out.println(e);
        }
    }

    public void loadFavorites() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream("favorites.dat")));
            favorites = (ArrayList<Movie>) inputStream.readObject();
        }
        catch (SecurityException | IOException |ClassNotFoundException e) {
            System.out.println(e);
        }
    }


}
