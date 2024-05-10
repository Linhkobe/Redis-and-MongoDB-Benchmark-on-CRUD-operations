package com.example.bigdatareddismongodbfilm.repositories.redis;

import com.example.bigdatareddismongodbfilm.entity.User;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface UserRedisRepository extends KeyValueRepository<User, String>{
}
