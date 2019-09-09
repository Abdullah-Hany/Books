package com.example.beido.books;

/**
 * Created by Beido on 7/12/2018.
 */

public class Books {

    private String title;
    private String language;
    private String image_link;
    private String preview_link;
    private String buy_link;
    private String country;
    private String currency_code;
    private String saleability;
    private String publisher ;
    private String publisher_date;
    private String [] authors ;
    private double price ;



    public Books(String title, String language, String image_link, String preview_link, String buy_link, String country, String currency_code, String saleability, double price, String publisher, String publisher_date, String[] authors) {
        this.title = title;
        this.language = language;
        this.image_link = image_link;
        this.preview_link = preview_link;
        this.buy_link = buy_link;
        this.country = country;
        this.currency_code = currency_code;
        this.saleability = saleability;
        this.price = price;
        this.publisher = publisher;
        this.authors = authors;
        this.publisher_date=publisher_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getPreview_link() {
        return preview_link;
    }

    public void setPreview_link(String preview_link) {
        this.preview_link = preview_link;
    }

    public String getBuy_link() {
        return buy_link;
    }

    public void setBuy_link(String buy_link) {
        this.buy_link = buy_link;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getPublisher_date() { return publisher_date;}

    public void setPublisher_date(String publisher_date) { this.publisher_date = publisher_date;}

}
