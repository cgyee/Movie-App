package com.example.movieapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
public class User implements Serializable {
    private String email;
    private ArrayList<Movie> favorites = new ArrayList<Movie>();

    public User(String email) {
        //Check if user email exists in db if not create user else return false
        this.email =email;
    }

    public void addMovie(String title, String poster_url) {
        if (!favorites.contains(new Movie(title, poster_url))) {
            favorites.add(new Movie(title, poster_url));
        }
    }

    public boolean removeMovie(Movie movie) {
        if(favorites.contains(movie)) {
            favorites.remove(movie);
            return true;
        }
        else {
            return false;
        }
    }

    /*public void saveFavorites() {
        try {
            ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream("favorites.dat")));
            ArrayList<Movie> output = favorites;
            out.writeObject(output);
            out.close();
        }
        catch (SecurityException | IOException e) {
            System.out.println(e);
        }
    }*/

    public byte [] saveFavorites() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(this);
            outputStream.flush();
            outputStream.close();
            byteArrayOutputStream.close();
        } catch (SecurityException | IOException e) {
            System.out.println(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*private void loadFavorites() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream("favorites.dat")));
            favorites = (ArrayList<Movie>) inputStream.readObject();
        }
        catch (SecurityException | IOException |ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Movie> getFavorites() {
        if(favorites !=null){
            return favorites;
        }
        else {
            loadFavorites();
            return favorites;
        }
    }*/

    public void loadFavorites(byte [] object) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(object);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            User temp = (User)objectInputStream.readObject();
            this.email = temp.getEmail();
            this.favorites = temp.getFavorites();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Movie> getFavorites() {
        return favorites;
    }

    public String getEmail() { return email;}
}
