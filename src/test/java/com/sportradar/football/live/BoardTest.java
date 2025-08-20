package com.sportradar.football.live;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void board_starts_game() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");

        var gameId = board.startGame(homeTeam, awayTeam);

        assertNotNull(gameId);
        assertEquals(0, gameId);
    }

    @Test
    void board_finishes_started_game() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");

        var gameId = board.startGame(homeTeam, awayTeam);
        var finishedGame = board.finishGame(gameId);

        assertNotNull(finishedGame);
        assertEquals(homeTeam, finishedGame.home());
        assertEquals(awayTeam, finishedGame.away());
        assertEquals(Score.START_SCORE, finishedGame.score());
    }

    @Test
    void board_throws_exception_on_attempt_to_finish_non_existing_game() {
        var exception = assertThrows(IllegalArgumentException.class, () -> board.finishGame(1));
        assertEquals("Game with id=[1] does not exist.", exception.getMessage());
    }

    @Test
    void board_updates_started_game_score() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");
        var gameId = board.startGame(homeTeam, awayTeam);
        var newScore = new Score(3, 2);
        board.updateScore(gameId, newScore);

        var updatedGame = board.finishGame(gameId);
        assertNotNull(updatedGame);
        assertEquals(newScore, updatedGame.score());
    }

    @Test
    void board_does_nothing_on_update_score_for_non_existing_game() {
        var score = new Score(3, 2);

        assertDoesNotThrow(() -> board.updateScore(1, score));
    }

    @Test
    void board_returns_summary_sorted_by_game_highest_total_score_and_start_time() {
        var homeTeam1 = new Team("Home1");
        var awayTeam1 = new Team("Away1");
        var homeTeam2 = new Team("Home2");
        var awayTeam2 = new Team("Away2");
        var homeTeam3 = new Team("Home3");
        var awayTeam3 = new Team("Away3");

        var gameId1 = board.startGame(homeTeam1, awayTeam1);
        var gameId2 = board.startGame(homeTeam2, awayTeam2);
        var gameId3 = board.startGame(homeTeam3, awayTeam3);

        var score1 = new Score(5, 3);
        var score2 = new Score(4, 4);
        var score3 = new Score(2, 1);

        board.updateScore(gameId1, score1);
        board.updateScore(gameId2, score2);
        board.updateScore(gameId3, score3);

        var summary = board.getSummary();

        assertEquals(3, summary.size());
        assertEquals("Home2 4-4 Away2", summary.get(0).toString());
        assertEquals("Home1 5-3 Away1", summary.get(1).toString());
        assertEquals("Home3 2-1 Away3", summary.get(2).toString());
    }

    @Test
    void board_removes_finished_games() {
        var homeTeam1 = new Team("Home1");
        var awayTeam1 = new Team("Away1");
        var homeTeam2 = new Team("Home2");
        var awayTeam2 = new Team("Away2");
        var homeTeam3 = new Team("Home3");
        var awayTeam3 = new Team("Away3");

        var gameId1 = board.startGame(homeTeam1, awayTeam1);
        var gameId2 = board.startGame(homeTeam2, awayTeam2);
        board.startGame(homeTeam3, awayTeam3);

        board.finishGame(gameId1);
        board.finishGame(gameId2);

        var summary = board.getSummary();

        assertEquals(1, summary.size());
        assertEquals(homeTeam3, summary.getFirst().home());
    }
}