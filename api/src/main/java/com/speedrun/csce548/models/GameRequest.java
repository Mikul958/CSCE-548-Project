package com.speedrun.csce548.models;

import java.time.LocalDate;

public class GameRequest
{
    private String title;
    private String description;
    private String thumbnailUrl;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;

    public Game fromRequest() {
        Game game = new Game(
            title,
            description,
            thumbnailUrl,
            releaseDate,
            developer,
            publisher
        );
        return game;
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
