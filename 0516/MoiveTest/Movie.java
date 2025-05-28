package com.example.movietest;

public class Movie {
    private int id;
    private String title;
    private int year;
    private String director;
    private double rating;
    private String country;

    public Movie() {}

    public Movie(String title, int year, String director, double rating, String country) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.country = country;
    }

    // Getter 및 Setter 메소드들
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
