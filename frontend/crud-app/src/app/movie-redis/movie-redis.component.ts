import { Component } from '@angular/core';
import {MovieService} from "../movie/movie.service";
import {MovieRedisService} from "./movie-redis.service";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-movie-redis',
  templateUrl: './movie-redis.component.html',
  styleUrl: './movie-redis.component.css'
})
export class MovieRedisComponent {
  searchID: string = '';
  movie: any = null;
  selectedMovie: any = null;
  movies: any[] = [];
  length = 100000;  // Replace with the actual total number of movies
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = {pageIndex: 0, pageSize: this.pageSize, length: this.length};

  constructor(private movieRedisService: MovieRedisService) {
  }

  ngOnInit(): void {
    this.loadMovies();
  }

  searchMovie(): void {
    this.movieRedisService.searchMovie(this.searchID).subscribe({
      next: movie => {
        this.movie = movie;
      },
      error: error => {
        console.error('Error searching for movie:', error);
      }
    });
  }

  updateMovie(movie: any): void {
    this.selectedMovie = {...movie};  // Make a copy of the movie to avoid modifying the original
  }

  submitUpdate(): void {
    this.movieRedisService.updateMovie(this.selectedMovie.id, this.selectedMovie).subscribe({
      next: () => {
        this.loadMovies();
        this.selectedMovie = null;  // Clear the selection
      },
      error: error => {
        console.error('Error updating movie:', error);
      }
    });
  }

  cancelUpdate(): void {
    this.selectedMovie = null;  // Clear the selection
  }

  deleteMovie(id: string): void {
    this.movieRedisService.deleteMovie(id).subscribe(() => {
      this.loadMovies();
    });
  }

  loadMovies(): void {
    this.movieRedisService.getMovies(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
      next: movies => {
        this.movies = movies.content;
        this.length = movies.totalElements;
      },
      error: error => {
        console.error('Error loading movies:', error);
      }
    });
  }
}
