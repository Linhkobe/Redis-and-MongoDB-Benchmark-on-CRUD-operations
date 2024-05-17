package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieServiceMongo {
    Movie createMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(String id);
    Movie updateMovie(String id, Movie movie);
    void deleteMovie(String id);
    Page<Movie> getAllMovies(Pageable pageable);
    void deleteMovieById(String id);
    List<Movie> getRandomMovies(int count);
    Movie getRandomMovie();
    Movie updateMovieBenchmark(String id, Movie movie);
    List<Movie> getFirstNMovies(int n);
}