package com.example.bigdatareddismongodbfilm.controllers.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.services.redis.MovieRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redis/movies")
public class MovieRedisController {

    @Autowired
    private MovieRedisService movieRedisService;

    @PostMapping("/create")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRedisService.saveMovie(movie);
        System.out.println("Movie crée");
        return ResponseEntity.ok(savedMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Optional<Movie> movie = movieRedisService.findMovieById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        Movie updateMovie = movieRedisService.updateMovie(id, updatedMovie);
        return ResponseEntity.ok(updateMovie);
    }

    @GetMapping("/paged")
    public Page<Movie> getAllMoviesPaged(Pageable pageable) {
        return movieRedisService.getAllMovies(pageable);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieRedisService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRedisService.findAllMovies();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(movies);
    }
}
