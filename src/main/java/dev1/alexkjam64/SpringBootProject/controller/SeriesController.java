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

import dev1.alexkjam64.SpringBootProject.repository.Series.SeriesInfo;
import dev1.alexkjam64.SpringBootProject.service.InvalidDataException;
import dev1.alexkjam64.SpringBootProject.service.NoDataException;
import dev1.alexkjam64.SpringBootProject.service.SeriesService;

@Controller
@RequestMapping("/series")
public class SeriesController extends Exception{
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable int id){
        System.out.println(id);
        try{
            var seriesVal = seriesService.retrieve(id);
            return ResponseEntity.ok(seriesVal);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> postInfo(@RequestBody SeriesInfo request, @PathVariable int id){
        try{
            seriesService.create(request, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Series ID already has series information!", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putInfo(@RequestBody SeriesInfo entity, @PathVariable int id){
        try{
            seriesService.update(entity, id);
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
            seriesService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NoDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}