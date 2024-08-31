package com.task.rockpaperscissors.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class Result {
    private Move playerMove;
    private Move computerMove;
    private Outcome outcome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return playerMove == result.playerMove && computerMove == result.computerMove && outcome == result.outcome;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerMove, computerMove, outcome);
    }

}


