package models;

import java.time.LocalDate;

public class Author
{
    private Integer id;
    private String username;
    private String displayName;
    private String passwordHash;
    private String profilePictureUrl;
    private LocalDate createDate;

    public Author() {}

    public Author(
        Integer id,
        String username,
        String displayName,
        String passwordHash,
        String profilePictureUrl,
        LocalDate createDate
    ) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.profilePictureUrl = profilePictureUrl;
        this.createDate = createDate;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
