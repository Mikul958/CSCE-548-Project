package com.speedrun.csce548.repository;

import com.speedrun.csce548.models.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer>
{

}
