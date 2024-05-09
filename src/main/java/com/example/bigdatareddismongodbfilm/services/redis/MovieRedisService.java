package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRedisService {
    Movie saveMovie(Movie movie);
    Optional<Movie> findMovieById(String id);
    void deleteMovie(String id);
    List<Movie> findAllMovies();
}
