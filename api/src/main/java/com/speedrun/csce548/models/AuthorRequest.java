package com.speedrun.csce548.models;

import java.time.LocalDate;

public class AuthorRequest
{
    private String username;
    private String displayName;
    private String password;
    private String profilePictureUrl;
    private LocalDate createDate;

    public Author fromRequest() {
        // TODO figure out password hashing
        String passwordHash = password;
        
        Author author = new Author(
            username,
            displayName,
            passwordHash,
            profilePictureUrl,
            createDate
        );
        return author;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
