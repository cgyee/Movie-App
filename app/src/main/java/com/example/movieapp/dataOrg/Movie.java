package com.example.movieapp.dataOrg;


import androidx.annotation.Nullable;
import java.io.Serializable;

/**
 * Object holds the title and poster url of a movie
 */
public class Movie implements Serializable{
    private String title;
    private String poster_url;

    /**
     * Constructor for Movie object
     * @param title String Title of Movie to create
     * @param poster_url String poster_url of Movie to create
     */
    public Movie(String title, String poster_url) {
        this.title =title;
        this.poster_url =poster_url;
    }

    /**
     * Getter for Movie.title
     * @return title
     */
    public String getTitle() {return title;}

    /**
     * Getter for Movie.poster_url
     * @return poster_url
     */
    public String getPoster_url() {return poster_url;}


    /**
     * Overrode the equals function for the Movie object so that if a movie exists in a list array it can be removed
     * @param obj Movie Object
     * @return id the two objects have the same contents
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        boolean isValid = false;
        if(obj == null || obj.getClass() != getClass()) {
            return isValid;
        }
        else  {
            Movie temp = (Movie) obj;
            if(this.getTitle().equals(temp.getTitle()) && this.getPoster_url().equals(temp.poster_url)) {
                isValid = true;
            }
        }
        return isValid;
    }
}
