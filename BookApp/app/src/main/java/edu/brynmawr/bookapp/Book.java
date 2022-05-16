package edu.brynmawr.bookapp;

import android.graphics.PaintFlagsDrawFilter;

import java.io.Serializable;

public class Book implements Serializable {
    String title;
    String author;
    String description;

    public Book(String title, String author, String description){
        this.title  = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
}