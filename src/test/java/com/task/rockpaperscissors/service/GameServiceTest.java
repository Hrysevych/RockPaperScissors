package com.task.rockpaperscissors.service;

import com.task.rockpaperscissors.model.Move;
import com.task.rockpaperscissors.model.Outcome;
import com.task.rockpaperscissors.model.Result;
import com.task.rockpaperscissors.model.Stats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.mockito.Mockito.*;

class GameServiceTest {

    private final GameService gameServiceActual = new GameService();
    private final GameService gameServiceSpy = spy(gameServiceActual);

    @Test
    void firstMove() {
        Result result = gameServiceSpy.move(Move.PAPER);
        Assertions.assertNotNull(Result.class);
        Assertions.assertSame(Result.class, result.getClass());
    }

    @Test
    void secondMove() {
        gameServiceSpy.move(Move.ROCK);
        Move playerMove = Move.PAPER;
        Result actual = gameServiceSpy.move(playerMove);
        Result expected = Result.builder().
                playerMove(playerMove).
                computerMove(Move.PAPER).
                outcome(Outcome.DRAW).
                build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getFirstStats() {
        Stats actual = gameServiceSpy.getStats();
        Stats expected = new Stats(0, 0, 0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getLaterStats() {
        gameServiceSpy.move(Move.ROCK);
        gameServiceSpy.move(Move.ROCK);
        gameServiceSpy.move(Move.ROCK);
        gameServiceSpy.move(Move.ROCK);
        Stats actual = gameServiceSpy.getStats();
        Assertions.assertTrue(actual.lost() >= 3);
    }
}