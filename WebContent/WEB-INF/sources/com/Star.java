package com;
import java.util.ArrayList;

public class Star {
	private int id; 
	private String first_name; 
	private String last_name; 
	private String dob; 
	private String photo_url; 
	//private ArrayList<String> starsIn; 
	
	public Star(){
		first_name = null;
		last_name = null;
		dob = null;
		photo_url = null;
		id = 0;
		
	}
	
	public Star(int id, String first_name, String last_name, String dob, String photo_url){
		this.id = id; 
		this.first_name = first_name; 
		this.last_name = last_name; 
		this.dob = dob;
		this.photo_url = photo_url;
	}
			 
	public int getId(){
		return id;
	}
	public String getFirstName(){
		return first_name;
	}
	public String getLastName(){
		return last_name;
	}
	public String getDob(){
		return dob;
	}
	public String getPhotoUrl(){
		return photo_url;
	}
	/*public ArrayList<String> getStarsIn(){
		return starsIn;
	}*/
	public String getFullName(){
		return first_name + " " + last_name; 
	}
}
