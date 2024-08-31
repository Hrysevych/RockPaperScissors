package com.task.rockpaperscissors.controller;

import com.task.rockpaperscissors.service.GameService;
import com.task.rockpaperscissors.model.Move;
import com.task.rockpaperscissors.model.Result;
import com.task.rockpaperscissors.model.Stats;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameController {

    private GameService service;

    @PostMapping("play")
    private Result play(@RequestBody Move move) {
        return service.move(move);
    }

    @PostMapping("stats")
    private Stats stats() {
        return service.getStats();
    }


}
