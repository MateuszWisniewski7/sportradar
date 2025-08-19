package com.sportradar.football.live;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void game_initializes_with_start_score() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");
        var game = new Game(homeTeam, awayTeam);

        assertEquals(Score.START_SCORE, game.score());
    }

    @Test
    void game_returns_total_score() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");
        var game = new Game(homeTeam, awayTeam);

        Game updateGame = game.updateScore(new Score(3, 1));

        assertEquals(4, updateGame.totalScore());
    }

    @Test
    void game_to_string_is_valid() {
        var homeTeam = new Team("Home");
        var awayTeam = new Team("Away");
        var game = new Game(homeTeam, awayTeam);

        assertEquals("Home 0-0 Away", game.toString());
    }
}