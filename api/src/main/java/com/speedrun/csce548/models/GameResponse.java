package com.speedrun.csce548.models;

import java.time.LocalDate;

public class GameResponse
{
    private Integer id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;
    
    public GameResponse(Game game) {
        id = game.getId();
        title = game.getTitle();
        description = game.getDescription();
        thumbnailUrl = game.getThumbnailUrl();
        releaseDate = game.getReleaseDate();
        developer = game.getDeveloper();
        publisher = game.getPublisher();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
