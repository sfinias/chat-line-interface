package com.sfinias.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CatModel {

	@JsonProperty("width")
	private int width;

	@JsonProperty("id")
	private String id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("breeds")
	private List<Object> breeds;

	@JsonProperty("height")
	private int height;

	public int getWidth(){
		return width;
	}

	public String getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}

	public List<Object> getBreeds(){
		return breeds;
	}

	public int getHeight(){
		return height;
	}

	@Override
	public String toString() {

		return "CatModel{" +
				"width=" + width +
				", id='" + id + '\'' +
				", url='" + url + '\'' +
				", breeds=" + breeds +
				", height=" + height +
				'}';
	}
}