package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.MovieRepository;
import com.example.bigdatareddismongodbfilm.services.redis.MovieRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@Service
public class MovieServiceMongoImpl implements MovieServiceMongo {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieRedisService movieRedisService;  // Autowired Redis Service

    @Override
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        movieRedisService.saveMovie(savedMovie);  // Save to Redis as well
        return savedMovie;
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
        Movie updatedMovie = movieRepository.save(movie);
        movieRedisService.saveMovie(updatedMovie);  // Update in Redis too
        return updatedMovie;
    }

    @Override
    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
        movieRedisService.deleteMovie(id);  // Delete from Redis as well
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    // Method to initialize Redis with data from MongoDB at startup
    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<Movie> allMovies = getAllMovies();  // Retrieve all movies from MongoDB
        allMovies.forEach(movieRedisService::saveMovie);  // Save each movie to Redis
        System.out.println("Initialized Redis with existing MongoDB data(MOVIE collection).");
    }
}
