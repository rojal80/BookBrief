package com.example.bookbrief;

public class ContentModel {

    private String title;
   private String description;

    private int img;

    public ContentModel(String title, String description) {
        this.title=title;
        this.description=description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static String cardHome;






}
