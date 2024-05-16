package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface MovieRedisService {
    Movie saveMovie(Movie movie);
    Optional<Movie> findMovieById(String id);
    void deleteMovie(String id);
    List<Movie> findAllMovies();
    Movie updateMovie(String id, Movie movie);

    Page<Movie> getAllMovies(Pageable pageable);
    Movie updateMovieBenchmark(String id, Movie movie);
}
