package com.example.bigdatareddismongodbfilm.repositories.redis;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RatingRedisRepository extends KeyValueRepository<Rating, String> {
}