package com.speedrun.csce548.repository;

import com.speedrun.csce548.models.Run;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run, Integer>
{

}
