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

@Service
public class BenchmarkService {

    @Autowired
    private MovieServiceMongo movieServiceMongo;

    @Autowired
    private MovieRedisService movieServiceRedis;

    private static final List<String> GENRES = Arrays.asList("Romance", "Action", "Comedy", "TV");
    private static final List<String> LANGUAGES = Arrays.asList("eng", "Italian", "Fr", "Spanish", "German");
    private static final Random RANDOM = new Random();

    public BenchmarkResult createMovies(int count, int runs) {
        try {
            long totalStartTime = 0;
            long totalEndTime = 0;
            double totalCpuLoad = 0.0;

            for (int i = 0; i < runs; i++) {
                long startTime = System.nanoTime();

                for (int j = 0; j < count; j++) {
                    Movie movie = generateRandomMovie();
                    movieServiceMongo.createMovie(movie);
                    movieServiceRedis.saveMovie(movie);
                }

                long endTime = System.nanoTime();
                totalStartTime += startTime;
                totalEndTime += endTime;

                OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
                for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
                    method.setAccessible(true);
                    if (method.getName().startsWith("getSystemCpuLoad")
                            && Modifier.isPublic(method.getModifiers())) {
                        try {
                            double cpuLoad = (double) method.invoke(operatingSystemMXBean);
                            totalCpuLoad += cpuLoad;
                            System.out.println("CPU Load for run " + (i+1) + ": " + cpuLoad);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            long averageTimeElapsed = (totalEndTime - totalStartTime) / runs;
            long averageTimeElapsedInMilliseconds = averageTimeElapsed / 1000000;
            double averageCpuLoad = totalCpuLoad / runs;

            System.out.println("Average execution time in nanoseconds: " + averageTimeElapsed);
            System.out.println("Average execution time in milliseconds: " + averageTimeElapsed / 1000000);
            System.out.println("Average CPU Load: " + averageCpuLoad);

            return new BenchmarkResult(averageTimeElapsed, averageTimeElapsedInMilliseconds, averageCpuLoad, runs);
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

    public void runBenchmarkAndDisplayResults(int count, int runs) {
        BenchmarkResult result = createMovies(count, runs);

        System.out.println("Average execution time in nanoseconds: " + result.getAverageTimeElapsed());
        System.out.println("Average execution time in milliseconds: " + result.getAverageTimeElapsedInMilliseconds());
        System.out.println("Average CPU Load: " + result.getAverageCpuLoad());
        System.out.println("Number of runs: " + result.getRuns());
    }
}
