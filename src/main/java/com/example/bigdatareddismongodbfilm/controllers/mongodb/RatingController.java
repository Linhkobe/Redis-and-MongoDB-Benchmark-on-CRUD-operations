// File: src/main/java/com/example/bigdatareddismongodbfilm/controllers/RatingController.java

package com.example.bigdatareddismongodbfilm.controllers.mongodb;

import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.services.mongodb.RatingServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongodb/ratings")
public class RatingController {

    @Autowired
    private RatingServiceMongo ratingService;

    @PostMapping
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
}