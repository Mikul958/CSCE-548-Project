package com.speedrun.csce548.models;

import java.time.LocalDate;

public class AuthorResponse
{
    private Integer id;
    private String username;
    private String displayName;
    private String profilePictureUrl;
    private LocalDate createDate;

    public AuthorResponse(Author author) {
        id = author.getId();
        username = author.getUsername();
        displayName = author.getDisplayName();
        profilePictureUrl = author.getProfilePictureUrl();
        createDate = author.getCreateDate();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
