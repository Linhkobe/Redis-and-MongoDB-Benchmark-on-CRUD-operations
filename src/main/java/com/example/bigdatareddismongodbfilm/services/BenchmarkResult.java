package com.example.bigdatareddismongodbfilm.services;

public class BenchmarkResult {
    private long averageTimeElapsedMongo;
    private long averageTimeElapsedInMillisecondsMongo;
    private long averageTimeElapsedRedis;
    private long averageTimeElapsedInMillisecondsRedis;
    private double averageCpuLoad;
    private int runs;

    public BenchmarkResult(long averageTimeElapsedMongo, long averageTimeElapsedInMillisecondsMongo, long averageTimeElapsedRedis, long averageTimeElapsedInMillisecondsRedis, double averageCpuLoad, int runs) {
        this.averageTimeElapsedMongo = averageTimeElapsedMongo;
        this.averageTimeElapsedInMillisecondsMongo = averageTimeElapsedInMillisecondsMongo;
        this.averageTimeElapsedRedis = averageTimeElapsedRedis;
        this.averageTimeElapsedInMillisecondsRedis = averageTimeElapsedInMillisecondsRedis;
        this.averageCpuLoad = averageCpuLoad;
        this.runs = runs;
    }

    public long getAverageTimeElapsedMongo() {
        return averageTimeElapsedMongo;
    }

    public long getAverageTimeElapsedInMillisecondsMongo() {
        return averageTimeElapsedInMillisecondsMongo;
    }

    public long getAverageTimeElapsedRedis() {
        return averageTimeElapsedRedis;
    }

    public long getAverageTimeElapsedInMillisecondsRedis() {
        return averageTimeElapsedInMillisecondsRedis;
    }

    public double getAverageCpuLoad() {
        return averageCpuLoad;
    }

    public int getRuns() {
        return runs;
    }

}
