package com.speedrun.csce548.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Run")
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "time_milliseconds", nullable = false)
    private Integer timeMilliseconds;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "set_date")
    private LocalDate setDate;

    @Column(name = "verified")
    private Boolean verified;

    protected Run() {}  // Empty constructor for Spring Data JPA, should not be called manually.

    public Run(
        Game game,
        Author author,
        String category,
        Integer timeMilliseconds,
        String videoUrl,
        LocalDate setDate,
        Boolean verified
    ) {
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
