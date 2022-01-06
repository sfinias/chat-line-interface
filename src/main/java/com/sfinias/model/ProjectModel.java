package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectModel{

	@JsonProperty("is_private")
	private boolean isPrivate;

	@JsonProperty("template")
	private boolean template;

	@JsonProperty("color")
	private String color;

	@JsonProperty("active")
	private boolean active;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("hex_color")
	private String hexColor;

	@JsonProperty("billable")
	private boolean billable;

	@JsonProperty("auto_estimates")
	private boolean autoEstimates;

	@JsonProperty("wid")
	private int wid;

	@JsonProperty("at")
	private String at;

	@JsonProperty("actual_hours")
	private int actualHours;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("cid")
	private int cid;

	public boolean isIsPrivate(){
		return isPrivate;
	}

	public boolean isTemplate(){
		return template;
	}

	public String getColor(){
		return color;
	}

	public boolean isActive(){
		return active;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getHexColor(){
		return hexColor;
	}

	public boolean isBillable(){
		return billable;
	}

	public boolean isAutoEstimates(){
		return autoEstimates;
	}

	public int getWid(){
		return wid;
	}

	public String getAt(){
		return at;
	}

	public int getActualHours(){
		return actualHours;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getCid(){
		return cid;
	}

	@Override
 	public String toString(){
		return 
			"ProjectModel{" + 
			"is_private = '" + isPrivate + '\'' + 
			",template = '" + template + '\'' + 
			",color = '" + color + '\'' + 
			",active = '" + active + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",hex_color = '" + hexColor + '\'' + 
			",billable = '" + billable + '\'' + 
			",auto_estimates = '" + autoEstimates + '\'' + 
			",wid = '" + wid + '\'' + 
			",at = '" + at + '\'' + 
			",actual_hours = '" + actualHours + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",cid = '" + cid + '\'' + 
			"}";
		}
}