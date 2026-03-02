package com.speedrun.csce548.models;

import java.time.LocalDate;

public class RunResponse
{
    private Integer id;
    private Game game;
    private Author author;
    private String category;
    private Integer timeMilliseconds;
    private String videoUrl;
    private LocalDate setDate;
    private Boolean verified;

    public RunResponse(Run run) {
        id = run.getId();
        game = run.getGame();
        author = run.getAuthor();
        category = run.getCategory();
        timeMilliseconds = run.getTimeMilliseconds();
        videoUrl = run.getVideoUrl();
        setDate = run.getSetDate();
        verified = run.getVerified();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTimeMilliseconds() {
        return timeMilliseconds;
    }

    public void setTimeMilliseconds(Integer timeMilliseconds) {
        this.timeMilliseconds = timeMilliseconds;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public LocalDate getSetDate() {
        return setDate;
    }

    public void setSetDate(LocalDate setDate) {
        this.setDate = setDate;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
