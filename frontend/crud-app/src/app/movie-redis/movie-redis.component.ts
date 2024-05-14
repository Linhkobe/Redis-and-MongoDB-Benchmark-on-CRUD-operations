import { Component } from '@angular/core';
import {MovieService} from "../movie/movie.service";
import {MovieRedisService} from "./movie-redis.service";
import {PageEvent} from "@angular/material/paginator";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-movie-redis',
  templateUrl: './movie-redis.component.html',
  styleUrl: './movie-redis.component.css'
})
export class MovieRedisComponent {
  searchID: string = '';
  movie: any = null;
  movieTitle : string = "";
  selectedMovie: any = null;
  movies: any[] = [];
  length = 100000;  // Replace with the actual total number of movies
  pageSize = 20;
  pageSizeOptions = [5, 10, 20, 50, 100];
  pageEvent: PageEvent = {pageIndex: 0, pageSize: this.pageSize, length: this.length};
  movieForm : FormGroup;
  createFormVisible: boolean = false;

  constructor(private movieRedisService: MovieRedisService, private fb: FormBuilder) {
    //Element du formulaire create
    this.movieForm = this.fb.group({
      id: [''],
      genres: this.fb.array([]),  // Un tableau pour les genres
      image_url: [''],
      imdb_id: [''],
      imdb_link: [''],
      movie_id: [''],
      movie_title: [''],
      original_language: [''],
      overview: [''],
      popularity: ['']
    });
  }
  //choix du genre dans create
  get genres(): FormArray {
    return this.movieForm.get('genres') as FormArray;
  }

  //enlever un genres dans le formulaire create
  removeGenre(index: number): void {
    this.genres.removeAt(index);
  }

// Méthode pour ajouter un nouveau champ genre dans le formulaire create
  addGenre(genre: string = ''): void {
    this.genres.push(new FormControl(genre));
  }

  //onsubmit du formulaire create
  onSubmit() {
    this.toggleCreateForm();
    console.log(this.movieForm.value);
    this.movieRedisService.createMovie(this.movieForm.value).subscribe({
      next: (response) => console.log('Movie created successfully!', response),
      error: (error) => console.error('Failed to create movie', error)
    });
  }

  fetchMovie(): void {
    this.movieRedisService.getMovieByTitle(this.movieTitle).subscribe({
      next: (movie) => this.movie = movie,
      error: (error) => console.error('Error fetching movie:', error)
    });
  }

  ngOnInit(): void {
    this.loadMovies();
  }

  toggleCreateForm() {
    this.createFormVisible = !this.createFormVisible;
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
