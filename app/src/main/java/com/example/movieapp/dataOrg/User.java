package com.example.movieapp.dataOrg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Organizes user data and Movie objects
 */
public class User implements Serializable {
    private String email;
    private ArrayList<Movie> favorites = new ArrayList<Movie>();

    /**
     * Creates a User object with the String email passed in the constructor
     * @param email email we wish to call this object
     */
    public User(String email) {
        this.email =email;
    }

    /**
     * Creates a new Movie object and adds Movie to  ArrayList favorites if not already in favorites
     * @param title String title of Movie to add
     * @param poster_url String poster_url of Movie to add
     */
    public void addMovie(String title, String poster_url) {
        if (!favorites.contains(new Movie(title, poster_url))) {
            favorites.add(new Movie(title, poster_url));
        }
    }

    /**
     * Removes Movie object from favorites ArrayList if found in list and returns boolean
     * @param movie Movie Object to remove from ArrayList favorites
     * @return If ArrayList favorites contains Movie
     */
    public boolean removeMovie(Movie movie) {
        if(favorites.contains(movie)) {
            favorites.remove(movie);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Serializes the User object as a byteStream then writes out User object as byte []
     * @return byte [] of User object
     */
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

    /**
     * Set the current Object to contents to the de-serialized objects values
     * @param object Serialized user object
     */
    public void loadFavorites(byte [] object) {
        if(object !=null) {
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
    }

    /**
     * Getter for User.favorites
     * @return favorites
     */
    public ArrayList<Movie> getFavorites() {
        return favorites;
    }

    /**
     * Getter for User.email
     * @return email
     */
    public String getEmail() { return email;}
}
