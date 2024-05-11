import { Component, OnInit } from '@angular/core';
import { MovieService } from './movie.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  movies: any[] = [];
  length = 100;  // Replace with the actual total number of movies
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = {pageIndex: 0, pageSize: this.pageSize, length: this.length};
  selectedMovie: any = null;
  searchID: string = '';
  movie: any = null;

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getMovies(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
      next: movies => {
        this.movies = movies.content;
        this.length = movies.totalElements;
      },
      error: error => {
        console.error('Error loading movies:', error);
      }
    });
  }


  searchMovie(): void {
    this.movieService.searchMovie(this.searchID).subscribe({
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
    this.movieService.updateMovie(this.selectedMovie.id, this.selectedMovie).subscribe({
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
    this.movieService.deleteMovie(id).subscribe(() => {
      this.loadMovies();
    });
  }
}
