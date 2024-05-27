package com.example.a4thtaskappkts.Model;

public class Model {
    String type, thumbNail, video, userName, countryImage, level, dollars, language, location, typeTxt;

    public Model(String type, String thumbNail, String video, String userName, String countryImage, String level, String dollars, String language, String location, String typeTxt) {
        this.type = type;
        this.thumbNail = thumbNail;
        this.video = video;
        this.userName = userName;
        this.countryImage = countryImage;
        this.level = level;
        this.dollars = dollars;
        this.language = language;
        this.location = location;
        this.typeTxt = typeTxt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountryImage() {
        return countryImage;
    }

    public void setCountryImage(String countryImage) {
        this.countryImage = countryImage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDollars() {
        return dollars;
    }

    public void setDollars(String dollars) {
        this.dollars = dollars;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTypeTxt() {
        return typeTxt;
    }

    public void setTypeTxt(String typeTxt) {
        this.typeTxt = typeTxt;
    }
}
