package dev1.alexkjam64.SpringBootProject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev1.alexkjam64.SpringBootProject.service.GameService;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;

@RestController
@RequestMapping("/games/batch")
public class GameBatchController {
    private final GameService gameService;

    public GameBatchController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<?> batchGame(@RequestBody List<Integer> ids) throws NoDataException{
        System.out.println("ids: " + ids);
        return ResponseEntity.ok(gameService.retrieveAllGames4Users(ids));
    }
}
