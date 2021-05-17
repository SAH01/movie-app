package com.example.cloudlibrary.Data;

public class ListData {
    private String img;
    private String title;
    private String url;
    private String date_time;
    private String score;
    private String scorenum;
    private String star;
    private String director;
    private String type_movie;
    private String area;
    private String summary;
    private String language_movie;
    private String timelen;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScorenum() {
        return scorenum;
    }

    public void setScorenum(String scorenum) {
        this.scorenum = scorenum;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getType_movie() {
        return type_movie;
    }

    public void setType_movie(String type_movie) {
        this.type_movie = type_movie;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage_movie() {
        return language_movie;
    }

    public void setLanguage_movie(String language_movie) {
        this.language_movie = language_movie;
    }

    public String getTimelen() {
        return timelen;
    }

    public void setTimelen(String timelen) {
        this.timelen = timelen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ListData(String title,String star,String director,String type_movie,String area
            ,String date_time,String summary,String score,String language_movie,String img,String scorenum
            ,String timelen) {
        this.img = img;
        this.title = title;
        this.date_time = date_time;
        this.score = score;
        this.scorenum = scorenum;
        this.star = star;
        this.director = director;
        this.type_movie = type_movie;
        this.area = area;
        this.summary = summary;
        this.language_movie = language_movie;
        this.timelen = timelen;
    }
}
