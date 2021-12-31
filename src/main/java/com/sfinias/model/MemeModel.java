package com.sfinias.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemeModel{

	@JsonProperty("preview")
	private List<String> preview;

	@JsonProperty("postLink")
	private String postLink;

	@JsonProperty("nsfw")
	private boolean nsfw;

	@JsonProperty("author")
	private String author;

	@JsonProperty("ups")
	private int ups;

	@JsonProperty("spoiler")
	private boolean spoiler;

	@JsonProperty("title")
	private String title;

	@JsonProperty("subreddit")
	private String subreddit;

	@JsonProperty("url")
	private String url;

	public List<String> getPreview(){
		return preview;
	}

	public String getPostLink(){
		return postLink;
	}

	public boolean isNsfw(){
		return nsfw;
	}

	public String getAuthor(){
		return author;
	}

	public int getUps(){
		return ups;
	}

	public boolean isSpoiler(){
		return spoiler;
	}

	public String getTitle(){
		return title;
	}

	public String getSubreddit(){
		return subreddit;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"MemeModel{" + 
			"preview = '" + preview + '\'' + 
			",postLink = '" + postLink + '\'' + 
			",nsfw = '" + nsfw + '\'' + 
			",author = '" + author + '\'' + 
			",ups = '" + ups + '\'' + 
			",spoiler = '" + spoiler + '\'' + 
			",title = '" + title + '\'' + 
			",subreddit = '" + subreddit + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}