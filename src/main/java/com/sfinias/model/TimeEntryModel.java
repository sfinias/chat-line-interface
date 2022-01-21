package com.sfinias.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeEntryModel{

	@JsonProperty("duration")
	private long duration;

	@JsonProperty("wid")
	private long wid;

	@JsonProperty("at")
	private Instant at;

	@JsonProperty("stop")
	private Instant stop;

	@JsonProperty("start")
	private Instant start;

	@JsonProperty("guid")
	private String guid;

	@JsonProperty("description")
	private String description;

	@JsonProperty("pid")
	private long pid;

	@JsonProperty("id")
	private long id;

	@JsonProperty("billable")
	private boolean billable;

	@JsonProperty("duronly")
	private boolean duronly;

	@JsonProperty("tags")
	private List<String> tags;

	public long getDuration(){
		return duration;
	}

	public long getWid(){
		return wid;
	}

	public Instant getAt(){
		return at;
	}

	public Instant getStop(){
		return stop;
	}

	public Instant getStart(){
		return start;
	}

	public String getGuid(){
		return guid;
	}

	public String getDescription(){
		return description;
	}

	public long getPid(){
		return pid;
	}

	public long getId(){
		return id;
	}

	public boolean isBillable(){
		return billable;
	}

	public boolean isDuronly(){
		return duronly;
	}

	public List<String> getTags(){
		return tags;
	}

	@Override
 	public String toString(){
		return 
			"TimeEntryModel{" + 
			"duration = '" + duration + '\'' + 
			",wid = '" + wid + '\'' + 
			",at = '" + at + '\'' + 
			",stop = '" + stop + '\'' + 
			",start = '" + start + '\'' + 
			",guid = '" + guid + '\'' + 
			",description = '" + description + '\'' + 
			",pid = '" + pid + '\'' + 
			",id = '" + id + '\'' + 
			",billable = '" + billable + '\'' + 
			",duronly = '" + duronly + '\'' + 
			",tags = '" + tags + '\'' + 
			"}";
		}
}