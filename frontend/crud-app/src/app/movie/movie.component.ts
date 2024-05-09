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

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.loadMovies();
  }

  loadMovies(): void {
    this.movieService.getMovies(this.pageEvent.pageIndex, this.pageEvent.pageSize).subscribe({
      next: movies => {
        this.movies = movies;
        this.length = movies.length;
      },
      error: error => {
        console.error('Error loading movies:', error);
      }
    });
  }

  updateMovie(id: string, movie: any): void {
    this.movieService.updateMovie(id, movie).subscribe({
      next: () => {
        this.loadMovies();
      },
      error: error => {
        console.error('Error updating movie:', error);
      }
    });
  }

  deleteMovie(id: string): void {
    this.movieService.deleteMovie(id).subscribe(() => {
      this.loadMovies();
    });
  }
}
