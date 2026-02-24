package com.speedrun.csce548.repository;

import com.speedrun.csce548.models.Game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer>
{

}
