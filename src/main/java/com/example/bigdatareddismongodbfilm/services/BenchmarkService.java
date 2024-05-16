package com.example.bigdatareddismongodbfilm.services;

import com.example.bigdatareddismongodbfilm.services.mongodb.MovieServiceMongo;
import com.example.bigdatareddismongodbfilm.services.redis.MovieRedisService;
import com.example.bigdatareddismongodbfilm.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.services.mongodb.RatingServiceMongo;
import com.example.bigdatareddismongodbfilm.services.redis.RatingRedisService;
import com.example.bigdatareddismongodbfilm.entity.User;
import com.example.bigdatareddismongodbfilm.services.mongodb.UserServiceMongo;
import com.example.bigdatareddismongodbfilm.services.redis.UserRedisService;

@Service
public class BenchmarkService {

    @Autowired
    private MovieServiceMongo movieServiceMongo;

    @Autowired
    private MovieRedisService movieServiceRedis;

    @Autowired
    private RatingServiceMongo ratingServiceMongo;

    @Autowired
    private RatingRedisService ratingServiceRedis;

    @Autowired
    private UserServiceMongo userServiceMongo;

    @Autowired
    private UserRedisService userServiceRedis;

    private static final List<String> GENRES = Arrays.asList("Romance", "Action", "Comedy", "TV");
    private static final List<String> LANGUAGES = Arrays.asList("eng", "Italian", "Fr", "Spanish", "German");
    private static final Random RANDOM = new Random();

    public BenchmarkResult createMovies(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                Movie movie = generateRandomMovie();
                movieServiceMongo.createMovie(movie);
                movieServiceRedis.saveMovie(movie);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Movie movie = generateRandomMovie();
                    movieServiceMongo.createMovie(movie);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Movie movie = generateRandomMovie();
                    movieServiceRedis.saveMovie(movie);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i + 1) + ": " + cpuLoad);
            }


            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            // You might want to modify the BenchmarkResult class to include separate results for MongoDB and Redis
            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Movie generateRandomMovie() {
        Movie movie = new Movie();
        movie.setId(UUID.randomUUID().toString());
        movie.setGenres(Arrays.asList(GENRES.get(RANDOM.nextInt(GENRES.size()))));
        movie.setImage_url("film-poster/" + UUID.randomUUID().toString());
        movie.setImdb_id(null);
        movie.setImdb_link(null);
        movie.setMovie_id("movie-" + (1980 + RANDOM.nextInt(40)));
        movie.setMovie_title("Movie " + UUID.randomUUID().toString());
        movie.setOriginal_language(LANGUAGES.get(RANDOM.nextInt(LANGUAGES.size())));
        movie.setOverview("This is a movie about " + UUID.randomUUID().toString());
        movie.setPopularity(String.valueOf(RANDOM.nextDouble()));
        movie.setNullField(null);
        return movie;
    }

    public BenchmarkResult findMovies(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;


            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<Movie> movies = getFirstNMovies(count);
                    Movie searchTerm = movies.get(j);
                    movieServiceMongo.getMovieById(searchTerm.getId());
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<Movie> movies = getFirstNMovies(count);
                    Movie searchTerm = movies.get(j);
                    movieServiceRedis.findMovieById(searchTerm.getId());
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
            }

            // Calculating average times and CPU load
            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            // Returning benchmark results
            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo,
                    averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis,
                    averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movie> getFirstNMovies(int n) {
        return movieServiceMongo.getFirstNMovies(n);
    }


    //Find n first ratings
    public BenchmarkResult findRatings(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;


            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<Rating> ratings = getFirstNRatings(count);
                    Rating searchTerm = ratings.get(j);
                    ratingServiceMongo.getRatingById(searchTerm.getId());
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<Rating> ratings = getFirstNRatings(count);
                    Rating searchTerm = ratings.get(j);
                    ratingServiceMongo.getRatingById(searchTerm.getId());
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
            }

            // Calculating average times and CPU load
            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            // Returning benchmark results
            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo,
                    averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis,
                    averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Rating> getFirstNRatings(int n) {
        return ratingServiceMongo.getFirstNRatings(n);
    }

    //Find n first users
    public BenchmarkResult findUsers(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;


            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<User> users = getFirstNUsers(count);
                    User searchTerm = users.get(j);
                    userServiceMongo.getUserById(searchTerm.getId());
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    List<User> users = getFirstNUsers(count);
                    User searchTerm = users.get(j);
                    userServiceRedis.findUserById(searchTerm.getId());
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
            }

            // Calculating average times and CPU load
            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            // Returning benchmark results
            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo,
                    averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis,
                    averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getFirstNUsers(int n) {
        return userServiceMongo.getFirstNUsers(n);
    }
/*    public Movie generateRandomSearchTerm() {
        // Obtenir la liste de tous les films depuis Redis
        //List<Movie> movies = movieServiceRedis.findAllMovies();
        List <Movie> movies = movieServiceMongo.getAllMovies();
        if (!movies.isEmpty()) {
            // Générer un index aléatoire dans la plage de films disponibles
            Random random = new Random();
            int randomIndex = random.nextInt(movies.size());

            // Renvoyer un film aléatoire
            return movies.get(randomIndex);
        } else {
            // Aucun film trouvé dans Redis
            return null;
        }
    }*/



    private Rating generateRandomRating() {
        Rating rating = new Rating();
        rating.setId(UUID.randomUUID().toString());
        rating.setMovie_id("movie-" + (1980 + RANDOM.nextInt(40)));
        rating.setRating_val(String.valueOf(RANDOM.nextInt(5) + 1));
        rating.setUser_id("user-" + (RANDOM.nextInt(10000)));
        return rating;
    }

    public BenchmarkResult createRatings(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                Rating rating = generateRandomRating();
                ratingServiceMongo.createRating(rating);
                ratingServiceRedis.saveRating(rating);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Rating rating = generateRandomRating();
                    ratingServiceMongo.createRating(rating);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Rating rating = generateRandomRating();
                    ratingServiceRedis.saveRating(rating);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i + 1) + ": " + cpuLoad);
            }

            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private User generateRandomUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setDisplay_name("User " + UUID.randomUUID().toString());
        user.setNum_ratings_pages(String.valueOf(RANDOM.nextInt(100)));
        user.setNum_reviews(String.valueOf(RANDOM.nextInt(100)));
        user.setUsername("username-" + UUID.randomUUID().toString());
        user.setNullField(null);
        return user;
    }

    public BenchmarkResult createUsers(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                User user = generateRandomUser();
                userServiceMongo.createUser(user);
                userServiceRedis.saveUser(user);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    User user = generateRandomUser();
                    userServiceMongo.createUser(user);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    User user = generateRandomUser();
                    userServiceRedis.saveUser(user);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i + 1) + ": " + cpuLoad);
            }

            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void runBenchmarkAndDisplayResults(int count, int runs) {
        BenchmarkResult result = createMovies(count, runs);

        if (result != null) {
            System.out.println("Average time elapsed for MongoDB: " + result.getAverageTimeElapsedMongo() + " nanoseconds");
            System.out.println("Average time elapsed for Redis: " + result.getAverageTimeElapsedRedis() + " nanoseconds");
            System.out.println("Average CPU Load: " + result.getAverageCpuLoad());
            System.out.println("Number of runs: " + result.getRuns());
        } else {
            System.out.println("Benchmark failed. Please check the logs for more details.");
        }
    }


    //UPDATE
    public Movie updateRandomMovie() {
        // Sélection aléatoire d'un film dans la base de données MongoDB
        Movie randomMovie = movieServiceMongo.getRandomMovie();
        if (randomMovie != null) {
            // Génération d'un nouvel ID aléatoire
            String newMovieId = "movie-" + (1980 + RANDOM.nextInt(40));

            // Modification de l'ID du film
            randomMovie.setId(newMovieId);
            randomMovie.setMovie_id(newMovieId); // Mise à jour de movie_id avec le nouvel ID
            System.out.println("Random movie updated with new ID: " + newMovieId);
            return randomMovie;


        } else {
            System.out.println("No movie found in the database.");
            return null;
        }
    }

    public BenchmarkResult updateMovies(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                Movie mov = this.updateRandomMovie();
                movieServiceMongo.updateMovieBenchmark(mov.getId(),mov);
                movieServiceRedis.updateMovieBenchmark(mov.getId(),mov);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Movie mov = this.updateRandomMovie();
                    movieServiceMongo.updateMovieBenchmark(mov.getId(),mov);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Movie movie = this.updateRandomMovie();
                    movieServiceRedis.updateMovieBenchmark(movie.getId(),movie);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
            }
            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Rating updateRandomRating() {
        Rating randomRating = ratingServiceMongo.getRandomRating();
        if (randomRating != null) {
            int newRatingVal = Integer.parseInt(randomRating.getRating_val()) + 1;
            randomRating.setRating_val(String.valueOf(newRatingVal));
            System.out.println("Random rating updated with new value: " + newRatingVal);
            return randomRating;
        } else {
            System.out.println("No rating found in the database.");
            return null;
        }
    }
    public BenchmarkResult updateRatings(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                Rating rating = this.updateRandomRating();
                ratingServiceMongo.updateRatingBenchmark(rating.getId(), rating);
                ratingServiceRedis.updateRatingBenchmark(rating.getId(), rating);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Rating rating = this.updateRandomRating();
                    ratingServiceMongo.updateRatingBenchmark(rating.getId(), rating);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    Rating rating = this.updateRandomRating();
                    ratingServiceRedis.updateRatingBenchmark(rating.getId(), rating);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
            }

            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public User updateRandomUser() {
        User randomUser = userServiceMongo.getRandomUser();
        if (randomUser != null) {
            String newDisplayName = randomUser.getDisplay_name() + " 2024";
            randomUser.setDisplay_name(newDisplayName);
            System.out.println("Random user updated with new display_name: " + newDisplayName);
            return randomUser;
        } else {
            System.out.println("No user found in the database.");
            return null;
        }
    }
    public BenchmarkResult updateUsers(int count, int runs) {
        try {
            long totalStartTimeMongo = 0;
            long totalEndTimeMongo = 0;
            long totalStartTimeRedis = 0;
            long totalEndTimeRedis = 0;
            double totalCpuLoad = 0.0;

            // Warm up the databases with some preliminary tests
            for (int i = 0; i < 10; i++) {
                User user = this.updateRandomUser();
                userServiceMongo.updateUserBenchmark(user.getId(), user);
                userServiceRedis.updateUserBenchmark(user.getId(), user);
            }

            for (int i = 0; i < runs; i++) {
                // Benchmark MongoDB
                long startTimeMongo = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    User user = this.updateRandomUser();
                    userServiceMongo.updateUserBenchmark(user.getId(), user);
                }
                long endTimeMongo = System.nanoTime();
                totalStartTimeMongo += startTimeMongo;
                totalEndTimeMongo += endTimeMongo;

                // Benchmark Redis
                long startTimeRedis = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    User user = this.updateRandomUser();
                    userServiceRedis.updateUserBenchmark(user.getId(), user);
                }
                long endTimeRedis = System.nanoTime();
                totalStartTimeRedis += startTimeRedis;
                totalEndTimeRedis += endTimeRedis;

                // Measure CPU load
                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                double cpuLoad = operatingSystemMXBean.getSystemLoadAverage();
                totalCpuLoad += cpuLoad;
                System.out.println("CPU Load for run " + (i + 1) + ": " + cpuLoad);
            }

            long averageTimeElapsedMongo = (totalEndTimeMongo - totalStartTimeMongo) / runs;
            long averageTimeElapsedRedis = (totalEndTimeRedis - totalStartTimeRedis) / runs;
            long averageTimeElapsedInMillisecondsMongo = averageTimeElapsedMongo / 1000000;
            long averageTimeElapsedInMillisecondsRedis = averageTimeElapsedRedis / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time for MongoDB in nanoseconds: " + averageTimeElapsedMongo);
            System.out.println("Average execution time for MongoDB in milliseconds: " + averageTimeElapsedInMillisecondsMongo);
            System.out.println("Average execution time for Redis in nanoseconds: " + averageTimeElapsedRedis);
            System.out.println("Average execution time for Redis in milliseconds: " + averageTimeElapsedInMillisecondsRedis);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsedMongo, averageTimeElapsedInMillisecondsMongo, averageTimeElapsedRedis, averageTimeElapsedInMillisecondsRedis, averageCpuLoad, runs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
