package com.sportradar.football.live;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

@Slf4j
@RequiredArgsConstructor
public class Board {

    private static final String GAME_DOES_NOT_EXIST = "Game with id=[%s] does not exist.";
    private final AtomicInteger id = new AtomicInteger();
    private final Map<Integer, Game> games = new ConcurrentHashMap<>();

    public Integer startGame(Team home, Team away) {
        int gameId = id.getAndIncrement();
        var game = new Game(home, away);
        games.put(gameId, game);
        log.info("Game started: id=[{}], game=[{}]", gameId, game);
        return gameId;
    }

    public Game finishGame(Integer id) {
        var game = games.remove(id);
        if (game != null) {
            log.info("Game finished: id=[{}], game=[{}]", id, game);
            return game;
        } else {
            throw new IllegalArgumentException(GAME_DOES_NOT_EXIST.formatted(id));
        }
    }

    public void updateScore(Integer id, Score score) {
        games.compute(id, (ignored, game) -> {
            if (game == null) {
                log.warn("Attempted to update score for a non-existing game with id=[{}]", id);
                return null;
            }
            log.info("Updating score for game id=[{}], new score=[{}]", id, score);
            return game.updateScore(score);
        });
    }

    public List<Game> getSummary() {
        return games.values().stream()
                .sorted(comparing(Game::totalScore).reversed()
                        .thenComparing(Game::startTime, reverseOrder()))
                .toList();
    }
}
