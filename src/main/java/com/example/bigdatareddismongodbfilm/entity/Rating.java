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

    private User user;
    private Movie movie;

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

    // User class
    public static class User {
        private String display_name;
        private String username;

        // Getters and setters
        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    // Movie class
    public static class Movie {
        private String movie_title;
        private List<String> genres;
        private String overview;

        // Getters and setters
        public String getMovie_title() {
            return movie_title;
        }

        public void setMovie_title(String movie_title) {
            this.movie_title = movie_title;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }
    }
}