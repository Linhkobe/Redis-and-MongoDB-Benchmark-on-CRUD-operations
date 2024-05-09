package com.example.bigdatareddismongodbfilm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;
import java.util.List;
import java.io.Serializable;

@RedisHash("User")
@Document(collection = "users_export")
public class User implements Serializable {

    @Id
    private String id;
    private String display_name;
    private String num_ratings_pages;
    private String num_reviews;
    private String username;

    @Field("null")
    private List<String> nullField;

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getNum_ratings_pages() {
        return num_ratings_pages;
    }

    public void setNum_ratings_pages(String num_ratings_pages) {
        this.num_ratings_pages = num_ratings_pages;
    }

    public String getNum_reviews() {
        return num_reviews;
    }

    public void setNum_reviews(String num_reviews) {
        this.num_reviews = num_reviews;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getNullField() {
        return nullField;
    }

    public void setNullField(List<String> nullField) {
        this.nullField = nullField;
    }
}