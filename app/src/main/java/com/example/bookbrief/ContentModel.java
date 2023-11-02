package com.example.bookbrief;

public class ContentModel {
    private String title ,image,userName;
    private String description;
    private String blogId;
    private int img;

    public ContentModel(String title, String description, String id,String userName,String image) {
        this.title = title;
        this.description = description;
        this.blogId = id;
        this.image=image;
        this.userName=userName;

    }

    public String getTitle() {
        return title;
    }

    public String getBlogId() {
        return blogId;
    }

    public String getImage(){
        return image;
    }

    public String getUserName(){
        return userName;
    }

    public void SetBlogId(String id) {
        this.blogId = id;
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
