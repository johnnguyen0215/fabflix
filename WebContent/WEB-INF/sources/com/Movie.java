package com;
import java.util.ArrayList;

public class Movie{
	private int id;
	private String title;
	private int year;
	private String director;
	private String bannerUrl;
	private String trailerUrl;
	private ArrayList<String> genres;
	private ArrayList<Star> stars;

	public Movie(int id, String title){
		this.id = id; 
		this.title = title; 
		this.director = "";
		this.bannerUrl = "";
		this.trailerUrl = "";
		this.genres = null;
		this.stars = null;
	}
	
	public Movie(int id, String title, int year, String director, String bannerUrl, String trailerUrl, ArrayList<String> genres, ArrayList<Star> stars){
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.bannerUrl = bannerUrl;
		this.trailerUrl = trailerUrl;
		this.genres = genres;
		this.stars = stars;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public int getYear(){
		return year;
	}

	public String getDirector(){
		return director;
	}

	public String getBannerUrl(){
		return bannerUrl;
	}

	public String getTrailerUrl(){
		return trailerUrl;
	}

	public ArrayList<String> getGenres(){
		return genres;
	}

	public ArrayList<Star> getStars(){
		return stars;
	}
}