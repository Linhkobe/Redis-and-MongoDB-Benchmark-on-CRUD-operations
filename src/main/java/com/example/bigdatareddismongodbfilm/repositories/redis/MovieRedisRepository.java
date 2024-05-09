package com.example.bigdatareddismongodbfilm.repositories.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface MovieRedisRepository extends KeyValueRepository<Movie, String> {
}
