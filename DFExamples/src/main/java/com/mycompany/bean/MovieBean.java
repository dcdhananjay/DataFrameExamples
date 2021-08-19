/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bean;

/**
 *
 * @author Dell
 */
public class MovieBean {

    private String movieName;
    private String rating;
    private String timestamp;

    public MovieBean() {

    }

    public MovieBean(String movieName, String rating, String timestamp) {
        this.movieName = movieName;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
