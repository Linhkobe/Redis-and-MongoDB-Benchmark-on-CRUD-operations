package com.example.bigdatareddismongodbfilm.controllers.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.services.mongodb.MovieServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;

import java.util.List;

@RestController
@RequestMapping("/mongodb/movies")
public class MovieMongoController {

    @Autowired
    private MovieServiceMongo movieService;

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @GetMapping("")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return movieService.getMovieById(objectId.toString());
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        return movieService.updateMovie(id, movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
    }

}