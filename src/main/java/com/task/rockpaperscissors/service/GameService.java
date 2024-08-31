package com.task.rockpaperscissors.service;


import com.task.rockpaperscissors.model.Move;
import com.task.rockpaperscissors.model.Outcome;
import com.task.rockpaperscissors.model.Result;
import com.task.rockpaperscissors.model.Stats;
import org.apache.commons.collections4.map.LRUMap;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@SessionScope
public class GameService {

    public final int LAST_USERS_MOVES_TO_REMEMBER = 5;
    private final Random RANDOM = new Random();
    private static final List<Move> MOVES = List.of(Move.values());
    private final LRUMap<Integer, Move> lastMoves = new LRUMap<>(LAST_USERS_MOVES_TO_REMEMBER);
    private final List<Result> rounds = new ArrayList<>();

    public Result move(Move playerMove) {
        Move computerMove = makeMove();
        int key = lastMoves.isEmpty() ? 1 : lastMoves.lastKey() + 1;
        lastMoves.put(key, playerMove);

        Result result = Result.builder()
                .playerMove(playerMove)
                .computerMove(computerMove)
                .outcome(playerMove.compareMoves(computerMove))
                .build();
        rounds.add(result);

        return result;
    }

    public Stats getStats() {
        Map<Outcome, List<Result>> results = rounds.stream().collect(Collectors.groupingBy(Result::getOutcome));

        int won = Optional.ofNullable(results.get(Outcome.WIN)).orElse(Collections.emptyList()).size();
        int lost = Optional.ofNullable(results.get(Outcome.LOSE)).orElse(Collections.emptyList()).size();
        int draw = Optional.ofNullable(results.get(Outcome.DRAW)).orElse(Collections.emptyList()).size();

        return new Stats(won, lost, draw);
    }

    private Move makeMove() {
        Optional<Move> mostFrequent = lastMoves.values().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return mostFrequent.map(move -> switch (move) {
            case ROCK -> Move.PAPER;
            case PAPER -> Move.SCISSORS;
            case SCISSORS -> Move.ROCK;
        }).orElseGet(() -> MOVES.get(RANDOM.nextInt(Move.values().length)));

    }

}
