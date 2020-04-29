package com.example.movieapp;


import androidx.annotation.Nullable;

import java.io.Serializable;

public class Movie implements Serializable{
    private String title;
    private String poster_url;

    public Movie(String title, String poster_url) {
        this.title =title;
        this.poster_url =poster_url;
    }

    public String getTitle() {return title;}
    public String getPoster_url() {return poster_url;}

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
