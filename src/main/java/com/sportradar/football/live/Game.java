package com.sportradar.football.live;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

public record Game(Team home, Team away, Instant startTime, Score score) {

    public Game {
        requireNonNull(home, "Home team cannot be null");
        requireNonNull(away, "Away team cannot be null");
        requireNonNull(startTime, "Start time cannot be null");
        requireNonNull(score, "Score cannot be null");
    }

    public Game(Team home, Team away) {
        this(home, away, Instant.now(), Score.START_SCORE);
    }

    public Game updateScore(Score newScore) {
        return new Game(home, away, startTime, newScore);
    }

    public int totalScore() {
        return score.total();
    }

    @Override
    public String toString() {
        return home.name() + " " + score.home() + "-" + score.away() + " " + away.name();
    }
}