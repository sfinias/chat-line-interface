package io.sfinias.cli.meme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MemeModel{

    @JsonProperty("preview")
    public List<String> preview;

    @JsonProperty("postLink")
    public String postLink;

    @JsonProperty("nsfw")
    public boolean nsfw;

    @JsonProperty("author")
    public String author;

    @JsonProperty("ups")
    public int ups;

    @JsonProperty("spoiler")
    public boolean spoiler;

    @JsonProperty("title")
    public String title;

    @JsonProperty("subreddit")
    public String subreddit;

    @JsonProperty("url")
    public String url;
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