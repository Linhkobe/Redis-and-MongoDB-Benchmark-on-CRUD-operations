package com.example.bigdatareddismongodbfilm.repositories.mongodb;

import com.example.bigdatareddismongodbfilm.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}