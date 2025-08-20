# Football World Cup Score Board - library

## Requirements

Java 21

## Description

This library provides classes to manage board of live football games and their scores.

## Features

- Start a game (returns a game ID, creates game with initial score 0:0)
- Update the score of a game by its ID (assumed that single publisher updates score of specific game - no score update
  time for comparison)
- Get a board summary (live games sorted by total score and start time - highest score and most recent on top)
- Finish a game and remove it from the board (returns finished game for processing)

## Thread safety

Library is thread-safe

## Usage example

```java
import com.sportradar.football.live.*;

var board = new Board();
var home = new Team("Poland");
var away = new Team("Brazil");
var score = new Score(2, 1);

var gameId = board.startGame(game);
board.updateScore(gameId, score);

var summary = board.getSummary();

var finishedGame = board.finishGame(gameId);
```

## Building

```
./gradlew build
```

## Testing

To run tests with jacoco report

```
./gradlew test
```

To run tests with coverage

```
./gradlew check
```