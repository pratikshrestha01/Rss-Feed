package com.boilerplate.model;

import com.dto.rssItem.SportCat;

public class RpmRssItemDto {
	long id;
	String title;
	String description;
	
	String url;
	
	String imageUrl;
	
	// eg espn,cnn,fox
	String source;
	
	// baseball, football,
	SportCat catagory;
	
	String sourceDate;
	
	String date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public SportCat getCatagory() {
		return catagory;
	}

	public void setCatagory(SportCat catagory) {
		this.catagory = catagory;
	}

	public String getSourceDate() {
		return sourceDate;
	}

	public void setSourceDate(String sourceDate) {
		this.sourceDate = sourceDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
