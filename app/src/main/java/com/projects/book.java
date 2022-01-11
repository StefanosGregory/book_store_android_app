package com.projects;

public class book {
    private String cover;
    private String title;
    private String title_el;
    private String type;
    private String type_el;
    private String description;
    private String description_el;
    private String author;
    private String price;
    private String quantity;


    public book(){}

    //Greek
    public String getTitle_el() {
        return title_el;
    }

    public void setTitle_el(String title_el) {
        this.title_el = title_el;
    }

    public String getType_el() {
        return type_el;
    }

    public void setType_el(String type_el) {
        this.type_el = type_el;
    }

    public String getDescription_el() {
        return description_el;
    }

    public void setDescription_el(String description_el) {
        this.description_el = description_el;
    }

    //English
    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
