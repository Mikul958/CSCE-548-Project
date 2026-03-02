package com.speedrun.csce548.models;

import java.time.LocalDate;

public class RunRequest
{
    private Integer gameId;
    private Integer authorId;
    private String category;
    private Integer timeMilliseconds;
    private String videoUrl;
    private LocalDate setDate;
    private Boolean verified;

    /**
     * Map a run request body to a run entity, including references to its game and author.
     * @param gameReference A provided reference to a game -- should be retrieved in service layer and passed in here.
     * @param authorReference A provided reference to an author -- should be retrieved in service layer and passed in here.
     * @return The mapped Run entity in the format it appears in the database.
     */
    public Run fromRequest(Game gameReference, Author authorReference) {
        Run run = new Run(
            gameReference,
            authorReference,
            category,
            timeMilliseconds,
            videoUrl,
            setDate,
            verified
        );
        return run;
    }


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
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
