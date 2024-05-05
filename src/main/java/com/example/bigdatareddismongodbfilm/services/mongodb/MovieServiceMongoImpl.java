// File: src/main/java/com/example/bigdatareddismongodbfilm/services/mongodb/MovieServiceMongoImpl.java

package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MovieServiceMongoImpl implements MovieServiceMongo {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie updateMovie(String id, Movie movie) {
        movie.setId(id);
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }
}