// File: src/main/java/com/example/bigdatareddismongodbfilm/repositories/mongodb/RatingRepository.java

package com.example.bigdatareddismongodbfilm.repositories.mongodb;
import org.springframework.data.mongodb.repository.Aggregation;
import com.example.bigdatareddismongodbfilm.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    @Aggregation(pipeline = {
            "{ $match: { user_id: ?0 } }",
            "{ $lookup: { from: 'users_export', localField: 'user_id', foreignField: 'username', as: 'user' } }",
            "{ $unwind: '$user' }",
            "{ $lookup: { from: 'movie_data', localField: 'movie_id', foreignField: 'movie_id', as: 'movie' } }",
            "{ $unwind: '$movie' }",
            "{ $project: { _id: 1, movie_id: 1, rating_val: 1, user_id: 1, 'user.display_name': 1, 'user.username': 1, 'movie.movie_title': 1, 'movie.genres': 1, 'movie.overview': 1 } }"
    })
    List<Rating> findByUserIdWithUserAndMovieDetails(String userId);

    @Aggregation(pipeline = {
            "{ $match: { movie_id: ?0 } }",
            "{ $lookup: { from: 'users_export', localField: 'user_id', foreignField: 'username', as: 'user' } }",
            "{ $unwind: '$user' }",
            "{ $project: { _id: 0, 'user.username': 1 } }"
    })
    List<String> findUsersWhoRatedMovie(String movieId);
}