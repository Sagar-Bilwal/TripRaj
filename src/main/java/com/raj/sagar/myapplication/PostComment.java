package com.raj.sagar.myapplication;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SAGAR on 18-03-2018.
 */

public class PostComment  {
    String username;
    String comment;

    PostComment()
    {

    }
    PostComment(String username,String comment)
    {
        this.username = username;
        this.comment = comment;
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
}
