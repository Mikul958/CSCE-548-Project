package models;

import java.time.LocalDate;

public class Run {

    private Integer id;
    private Game game;
    private Author author;
    private String category;
    private Integer timeMilliseconds;
    private String videoUrl;
    private LocalDate setDate;
    private Boolean verified;

    public Run() {}

    public Run(
        Integer id,
        Game game,
        Author author,
        String category,
        Integer timeMilliseconds,
        String videoUrl,
        LocalDate setDate,
        Boolean verified
    ) {
        this.id = id;
        this.game = game;
        this.author = author;
        this.category = category;
        this.timeMilliseconds = timeMilliseconds;
        this.videoUrl = videoUrl;
        this.setDate = setDate;
        this.verified = verified;
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

    public void setGameId(Game game) {
        this.game = game;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthorId(Author author) {
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
