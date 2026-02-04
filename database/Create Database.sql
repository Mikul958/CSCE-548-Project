CREATE DATABASE IF NOT EXISTS speedrun_app;
USE speedrun_app;

CREATE USER IF NOT EXISTS 'speedrun_db'@'localhost'
IDENTIFIED WITH caching_sha2_password BY 'notmyrootpassword321';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE, SHOW VIEW
ON speedrun_app.*
TO 'speedrun_db'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE Game (
    id INT AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    thumbnail_url VARCHAR(500),
    release_date DATE,
    developer VARCHAR(50),
    publisher VARCHAR(50),
    
    PRIMARY KEY (id)
);

CREATE TABLE Author (
	id INT AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE,
    display_name VARCHAR(30) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    profile_picture_url VARCHAR(500),
    create_date DATE,
    
    PRIMARY KEY (id)
);

CREATE TABLE Run (
	id INT AUTO_INCREMENT,
    game_id INT,
    author_id INT,
    category VARCHAR(30) NOT NULL,
    time_milliseconds INT UNSIGNED NOT NULL,
    video_url VARCHAR(500),
    set_date DATE,
    verified BOOLEAN,
    
    PRIMARY KEY (id),
    CONSTRAINT game_relation
		FOREIGN KEY (game_id)
        REFERENCES Game(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT author_relation    
		FOREIGN KEY (author_id)
        REFERENCES Author(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);