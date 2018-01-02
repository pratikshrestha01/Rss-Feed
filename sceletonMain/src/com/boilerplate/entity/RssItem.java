package com.boilerplate.entity;

import javax.persistence.Entity;

import com.dto.rssItem.SportCat;

@Entity
public class RssItem extends AbstractEntity<Long>{
	
	
	String title;
	String description;
	
	String url;
	
	String imageUrl;
	
	// eg espn,cnn,fox
	String source;
	
	// baseball, football,
	SportCat catagory;
	
	String sourceDate;

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
	
	
}
