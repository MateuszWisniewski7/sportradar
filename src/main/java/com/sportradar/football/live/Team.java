package com.sportradar.football.live;

import static java.util.Objects.requireNonNull;

public record Team(String name) {

    public Team {
        requireNonNull(name, "Team name cannot be null");
    }
}
