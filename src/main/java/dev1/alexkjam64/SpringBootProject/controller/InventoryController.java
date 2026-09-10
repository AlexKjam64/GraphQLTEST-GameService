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

import dev1.alexkjam64.SpringBootProject.repository.Inventory.InventoryInfo;
import dev1.alexkjam64.SpringBootProject.service.InvalidDataException;
import dev1.alexkjam64.SpringBootProject.service.InventoryService;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;

@Controller
@RequestMapping("/inventory")
public class InventoryController extends Exception{
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{userId}/{gameId}")
    public ResponseEntity<?> getInfo(@PathVariable int userId, @PathVariable int gameId){
        try{
            var invVal = inventoryService.retrieve(userId, gameId);
            return ResponseEntity.ok(invVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postInfo(@RequestBody InventoryInfo request){
        try{
            inventoryService.create(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Inventory information already exist!", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{userId}/{gameId}")
    public ResponseEntity<?> putInfo(@RequestBody InventoryInfo entity, @PathVariable int userId, @PathVariable int gameId){
        try{
            inventoryService.update(entity, userId, gameId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}-{gameId}")
    public ResponseEntity<?> deleteInfo(@PathVariable int userId, @PathVariable int gameId){
        try{
            inventoryService.delete(userId, gameId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}