package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;

import java.util.List;

public interface MovieServiceMongo {
    Movie createMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(String id);
    Movie updateMovie(String id, Movie movie);
    void deleteMovie(String id);
}