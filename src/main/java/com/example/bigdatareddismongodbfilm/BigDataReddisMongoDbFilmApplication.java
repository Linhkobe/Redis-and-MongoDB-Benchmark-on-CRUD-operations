package com.example.bigdatareddismongodbfilm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.bigdatareddismongodbfilm.repositories.mongodb")
@EnableRedisRepositories(basePackages = "com.example.bigdatareddismongodbfilm.repositories.redis")
public class BigDataReddisMongoDbFilmApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigDataReddisMongoDbFilmApplication.class, args);
    }

}
