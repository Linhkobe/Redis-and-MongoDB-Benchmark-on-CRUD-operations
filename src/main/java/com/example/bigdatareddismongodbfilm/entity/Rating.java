package com.example.bigdatareddismongodbfilm.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;
import java.util.List;
import java.io.Serializable;

@RedisHash("Rating")

//@Document(collection = "rating_data")
@Document(collection = "ratings_export")
public class Rating implements Serializable {

    @Id
    private String id;
    private String movie_id;
    private String rating_val;
    private String user_id;

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getRating_val() {
        return rating_val;
    }

    public void setRating_val(String rating_val) {
        this.rating_val = rating_val;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}