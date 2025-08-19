package com.sportradar.football.live;

public record Score(int home, int away) {
    static final Score START_SCORE = new Score(0, 0);

    public Score {
        if (home < 0 || away < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    int total() {
        return home + away;
    }
}
