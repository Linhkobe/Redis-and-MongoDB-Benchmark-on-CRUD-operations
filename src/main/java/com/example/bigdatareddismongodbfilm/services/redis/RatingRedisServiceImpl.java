package com.example.bigdatareddismongodbfilm.services.redis;

import com.example.bigdatareddismongodbfilm.entity.Movie;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import com.example.bigdatareddismongodbfilm.repositories.redis.RatingRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Service
public class RatingRedisServiceImpl implements RatingRedisService {

    private final RatingRedisRepository ratingRedisRepository;

    @Override
    public List<Rating> getFirstNRatings(int n) {
        Pageable pageable = PageRequest.of(0, n);
        Page<Rating> ratingPage = ratingRedisRepository.findAll(pageable);
        return ratingPage.getContent();
    }

    @Autowired
    public RatingRedisServiceImpl(RatingRedisRepository ratingRedisRepository) {
        this.ratingRedisRepository = ratingRedisRepository;
    }
    @Override
    public Rating updateRating(String id, Rating rating) {
        rating.setId(id);
        return ratingRedisRepository.save(rating);
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRedisRepository.save(rating);
    }

    @Override
    public Optional<Rating> findRatingById(String id) {
        return ratingRedisRepository.findById(id);
    }

    @Override
    public void deleteRating(String id) {
        ratingRedisRepository.deleteById(id);
    }

    @Override
    public Page<Rating> getAllRatings(Pageable pageable) {
        return ratingRedisRepository.findAll(pageable);
    }

    @Override
    public List<Rating> findAllRatings() {
        return ratingRedisRepository.findAll();
    }

    public Rating updateRatingBenchmark(String id, Rating rating) {
        rating.setId(id);
        Rating updatedRating = ratingRedisRepository.save(rating);
        return updatedRating;
    }
}
