package com.example.bigdatareddismongodbfilm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class BenchmarkController {

    @Autowired
    private BenchmarkService benchmarkService;

    @PostMapping("/api/benchmark/createMovies")
    public BenchmarkResult createMovies(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.createMovies(count, runs);
    }
    @PostMapping("/api/benchmark/createRatings")
    public BenchmarkResult createRatings(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.createRatings(count, runs);
    }

    @PostMapping("/api/benchmark/createUsers")
    public BenchmarkResult createUsers(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.createUsers(count, runs);
    }

    @PatchMapping("/api/benchmark/updateMovies")
    public BenchmarkResult updateMovies(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.updateMovies(count, runs);
    }
    @PatchMapping("/api/benchmark/updateRatings")
    public BenchmarkResult updateRating(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.updateRatings(count, runs);
    }
    @PatchMapping("/api/benchmark/updateUsers")
    public BenchmarkResult updateUsers(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.updateUsers(count, runs);
    }

    @GetMapping("/api/benchmark/findMovies")
    public BenchmarkResult findMovies(@RequestParam int count, @RequestParam int runs) {
        System.out.println("Received request with Count: " + count + ", Runs: " + runs);
        return benchmarkService.findMovies(count, runs);
    }

}
