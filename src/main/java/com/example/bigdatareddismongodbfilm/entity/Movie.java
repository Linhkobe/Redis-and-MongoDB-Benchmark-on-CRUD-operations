package com.example.bigdatareddismongodbfilm.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movie_data")
public class Movie {

    @Id
    private String id;
    private List<String> genres;
    private String image_url;
    private String imdb_id;
    private String imdb_link;
    private String movie_id;
    private String movie_title;
    private String original_language;
    private String overview;
    private String popularity;
    private List<String> nullField;

    // getters
    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getImdb_link() {
        return imdb_link;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public List<String> getNullField() {
        return nullField;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setImdb_link(String imdb_link) {
        this.imdb_link = imdb_link;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setNullField(List<String> nullField) {
        this.nullField = nullField;
    }
}