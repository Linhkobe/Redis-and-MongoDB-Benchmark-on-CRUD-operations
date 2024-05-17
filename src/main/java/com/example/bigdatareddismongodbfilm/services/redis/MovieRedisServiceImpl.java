package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.repositories.redis.MovieRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.List;
import java.util.Optional;

@Service
public class MovieRedisServiceImpl implements MovieRedisService {

    @Autowired
    private MovieRedisRepository movieRedisRepository;

    @Override
    public List<Movie> getFirstNMovies(int n) {
        Pageable pageable = PageRequest.of(0, n);
        Page<Movie> moviePage = movieRedisRepository.findAll(pageable);
        return moviePage.getContent();
    }

    @Override
    public Movie updateMovie(String id, Movie movie) {
        movie.setId(id);
        return movieRedisRepository.save(movie);
    }
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

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRedisRepository.findAll(pageable);}



    @Override
    public Movie updateMovieBenchmark(String id, Movie movie) {
        movie.setId(id);
        Movie updatedMovie = movieRedisRepository.save(movie);
        return updatedMovie;
    }
    public void deleteMovieById(String id) {
        movieRedisRepository.deleteById(id);
    }


}
