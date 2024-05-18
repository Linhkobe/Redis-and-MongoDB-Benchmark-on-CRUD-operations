package com.example.bigdatareddismongodbfilm.services.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.MovieRepository;
import com.example.bigdatareddismongodbfilm.services.redis.MovieRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;


@Service
public class MovieServiceMongoImpl implements MovieServiceMongo {
    @Autowired
    private Environment env;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getFirstNMovies(int n) {
        Pageable pageable = PageRequest.of(0, n);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        return moviePage.getContent();
    }

    @Autowired
    private MovieRedisService movieRedisService;  // Autowired Redis Service

    @Override
    public Movie createMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
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
    public Movie updateMovieBenchmark(String id, Movie movie) {
        movie.setId(id);
        Movie updatedMovie = movieRepository.save(movie);
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

    // Method to initialize Redis with data from MongoAtlas at startup
    @EventListener(ContextRefreshedEvent.class)
    public void initRedisWithMongoData() {
        List<Movie> allMovies = getAllMoviesFromAtlas();  // Retrieve all movies from MongoDB Atlas
        allMovies.forEach(movieRedisService::saveMovie);  // Save each movie to Redis
        System.out.println("Initialized Redis with existing MongoDB Atlas data(MOVIE collection).");
    }

    // Method to initialize local MongoDB with data from MongoDB Atlas at startup
    @EventListener(ContextRefreshedEvent.class)
    public void initLocalMongoWithAtlasData() {
        List<Movie> allMovies = getAllMoviesFromAtlas();  // Retrieve all movies from MongoDB Atlas
        allMovies.forEach(movieRepository::save);  // Save each movie to local MongoDB
        System.out.println("Initialized local MongoDB with existing MongoDB Atlas data(MOVIE collection).");
    }



// ...

    public List<Movie> getAllMoviesFromAtlas() {
        try {
            String atlasUri = Objects.requireNonNull(env.getProperty("SPRING_DATA_MONGODB_ATLAS_URI"));
            System.out.println("MongoDB Atlas URI: " + atlasUri);

            MongoTemplate mongoTemplateAtlas = new MongoTemplate(new SimpleMongoClientDatabaseFactory(atlasUri));

            Query query = new Query();
            query.limit(1000);  // Limit to 1000 records

            List<Movie> movies = mongoTemplateAtlas.find(query, Movie.class);

            System.out.println("Number of movies retrieved from MongoDB Atlas: " + movies.size());
            return movies;
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving movies from MongoDB Atlas:");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Movie getRandomMovie() {
        // Compter le nombre total de films dans la base de données
        long movieCount = movieRepository.count();
        if (movieCount > 0) {
            // Générer un index aléatoire dans la plage de films disponibles
            Random random = new Random();
            int randomIndex = random.nextInt((int) movieCount);

            // Obtenir une page contenant un film aléatoire
            Pageable pageable = PageRequest.of(randomIndex, 1);
            List<Movie> movies = movieRepository.findAll(pageable).getContent();

            // Renvoyer le premier film trouvé dans la page
            return movies.get(0);
        } else {
            // Aucun film trouvé dans la base de données
            return null;
        }
    }

    @Override
    public void deleteMovieById(String id) {
        movieRepository.deleteById(id);
        movieRedisService.deleteMovie(id);  // Delete from Redis as well
    }
    public List<Movie> getRandomMovies(int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by("id").ascending());

        List<Movie> randomMovies = movieRepository.findAll(pageable).getContent();

        return randomMovies;
    }



}
