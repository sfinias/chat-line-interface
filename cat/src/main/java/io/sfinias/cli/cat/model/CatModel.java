package io.sfinias.cli.cat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CatModel {

	@JsonProperty("width")
	public int width;

	@JsonProperty("id")
	public String id;

	@JsonProperty("url")
	public String url;

	@JsonProperty("breeds")
	public List<Object> breeds;

	@JsonProperty("height")
	public int height;

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