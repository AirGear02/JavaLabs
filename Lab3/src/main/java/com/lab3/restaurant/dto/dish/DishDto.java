package com.lab3.restaurant.dto.dish;

import java.io.Serializable;

public class DishDto implements Serializable {
    private String title;
    private String description;
    private String photoPath;
    private long id;

    public DishDto(long id, String title, String description, String photoPath) {
        this.title = title;
        this.description = description;
        this.photoPath = photoPath;
        this.id = id;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
