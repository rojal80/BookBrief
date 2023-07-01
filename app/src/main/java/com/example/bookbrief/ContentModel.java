package com.example.bookbrief;

public class ContentModel {

    private String title;
   private String description;

    private int img;

    public ContentModel(String title, String description, int image) {
        this.title=title;
        this.description=description;
        this.img=image;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public static String cardHome;






}
