package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.repositories.redis.MovieRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieRedisServiceImpl implements MovieRedisService {

    @Autowired
    private MovieRedisRepository movieRedisRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRedisRepository.save(movie);
    }

    @Override
    public Optional<Movie> findMovieById(String id) {
        return movieRedisRepository.findById(id);
    }

    @Override
    public void deleteMovie(String id) {
        movieRedisRepository.deleteById(id);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRedisRepository.findAll();
    }
}