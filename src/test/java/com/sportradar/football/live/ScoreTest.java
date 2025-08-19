package com.sportradar.football.live;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreTest {

    @Test
    void score_returns_sum_of_home_and_away_score() {
        Score score = new Score(3, 2);

        assertEquals(5, score.total());
    }

    @ParameterizedTest(name = "home={0}, away={1}")
    @CsvSource({
            "-1, 0",
            "0, -2",
            "-4, -8"
    })
    void score_throws_exception_on_negative_values(int home, int away) {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Score(home, away));
        assertEquals("Scores cannot be negative", exception.getMessage());

    }
}