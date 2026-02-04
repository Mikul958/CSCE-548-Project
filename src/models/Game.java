package models;

import java.time.LocalDate;

public class Game
{
    private Integer id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;

    public Game() {}

    public Game(
        Integer id,
        String title,
        String description,
        String thumbnailUrl,
        LocalDate releaseDate,
        String developer,
        String publisher
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
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