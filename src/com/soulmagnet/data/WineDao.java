package com.soulmagnet.data;

import java.util.ArrayList;

public class WineDao
{

	private String w_id;
	public String getW_id() {
		return w_id;
	}
	public void setW_id(String w_id) {
		this.w_id = w_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image + ".jpg";
	}
	public void setImage(String image) {
		this.image = image;
	}
	private String name;
	private String type;
	private String description;
	private String image;
	
	
	
}
