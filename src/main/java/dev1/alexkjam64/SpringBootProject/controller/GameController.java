package dev1.alexkjam64.SpringBootProject.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev1.alexkjam64.SpringBootProject.repository.Game.GameInfo;
import dev1.alexkjam64.SpringBootProject.service.GameService;
import dev1.alexkjam64.SpringBootProject.service.InvalidDataException;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;

@Controller
@RequestMapping("/games")
public class GameController extends Exception{
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllInfo(@PathVariable int userId){
        System.out.println("id: " + userId);
        try{
            var gameVal = gameService.retrieveAllGames(userId);
            return ResponseEntity.ok(gameVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable int id){
        System.out.println("id: " + id);
        try{
            var gameVal = gameService.retrieve(id);
            return ResponseEntity.ok(gameVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> postInfo(@RequestBody GameInfo request, @PathVariable int id){
        try{
            gameService.create(request, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Game ID already has game information!", HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> putInfo(@RequestBody GameInfo entity, @PathVariable int id) {
        try{
            gameService.update(entity, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInfo(@PathVariable int id){
        try{
            gameService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
