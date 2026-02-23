package com.speedrun.csce548.repository;

import com.speedrun.csce548.models.Run;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RunRepository extends JpaRepository<Run, Integer>
{
    /**
     * Automatically returns a list of runs set by the author with the given ID, sorted by newest to oldest.
     * @param authorId The ID of the author setting the list of runs.
     * @return An effective history of runs for the given author.
     */
    List<Run> findByAuthor_IdOrderBySetDateDesc(Integer authorId);

    /**
     * Automatically returns a list of runs for a given game and category, sorted by fastest to slowest.
     * @param gameId The target game the runs were set on.
     * @param category The target category that runs were set on.
     * @return An effective leaderboard for the given game and categpry.
     */
    List<Run> findByGame_IdAndCategoryOrderByTimeMillisecondsAsc(Integer gameId, String category);

    /**
     * Returns a list of categories for the given game.
     * @param gameId The target game to search categories for.
     * @return An effective set of existing/navigable categories for a game.
     */
    @Query("""
        SELECT DISTINCT run.category
        FROM Run run
        WHERE run.game.id = :gameId
    """)
    List<String> findDistinctCategoriesByGameId(@Param("gameId") Integer gameId);
}
