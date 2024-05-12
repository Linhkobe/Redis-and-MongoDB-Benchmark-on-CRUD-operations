package com.example.bigdatareddismongodbfilm.services;

public class BenchmarkResult {
    private long averageTimeElapsed;
    private long averageTimeElapsedInMilliseconds;
    private double averageCpuLoad;
    private int runs;

    public BenchmarkResult(long averageTimeElapsed, long averageTimeElapsedInMilliseconds, double averageCpuLoad, int runs) {
        this.averageTimeElapsed = averageTimeElapsed;
        this.averageTimeElapsedInMilliseconds = averageTimeElapsedInMilliseconds;
        this.averageCpuLoad = averageCpuLoad;
        this.runs = runs;
    }

    public long getAverageTimeElapsed() {
        return averageTimeElapsed;
    }

    public long getAverageTimeElapsedInMilliseconds() {
        return averageTimeElapsedInMilliseconds;
    }

    public double getAverageCpuLoad() {
        return averageCpuLoad;
    }

    public int getRuns() {
        return runs;
    }
}
