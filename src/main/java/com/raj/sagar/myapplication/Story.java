package com.raj.sagar.myapplication;

import android.net.Uri;

/**
 * Created by SAGAR on 17-03-2018.
 */

public class Story {

    private String city, username, comment,image;

    public Story() {

    }

    public Story(String city, String username, String comment, String image) {
        this.city = city;
        this.username = username;
        this.comment = comment;
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
