// File: src/main/java/com/example/bigdatareddismongodbfilm/controllers/RatingController.java

package com.example.bigdatareddismongodbfilm.controllers.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.mongodb.RatingRepository;
import com.example.bigdatareddismongodbfilm.services.mongodb.RatingServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongodb/ratings")
public class RatingController {

    @Autowired
    private RatingServiceMongo ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.ok(ratingService.createRating(rating));
    }

    @GetMapping("/")
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable String id) {
        Rating rating = ratingService.getRatingById(id);
        if (rating != null) {
            return ResponseEntity.ok(rating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable String id, @RequestBody Rating rating) {
        return ResponseEntity.ok(ratingService.updateRating(id, rating));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable String id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<Rating> getAllRatingsPaged(Pageable pageable) {
        return ratingService.getAllRatings(pageable);
    }


    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable String userId) {
        return ratingService.findByUserIdWithUserAndMovieDetails(userId);
    }

    @GetMapping("/movie/{movieId}/users")
    public List<String> getUsersWhoRatedMovie(@PathVariable String movieId) {
        return ratingService.findUsersWhoRatedMovie(movieId);
    }
}