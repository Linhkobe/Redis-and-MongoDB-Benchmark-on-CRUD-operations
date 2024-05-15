import { Component, OnInit } from '@angular/core';
import { MovieService } from './movie.service';
import { PageEvent } from '@angular/material/paginator';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';

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

  movieForm: FormGroup;
  createFormVisible: boolean = false;

  constructor(private movieService: MovieService, private fb: FormBuilder) {
    this.movieForm = this.fb.group({
      id: [''],
      genres: this.fb.array([]),
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

  get genres(): FormArray {
    return this.movieForm.get('genres') as FormArray;
  }

  removeGenre(index: number): void {
    this.genres.removeAt(index);
  }

  addGenre(genre: string = ''): void {
    this.genres.push(new FormControl(genre));
  }

  onSubmit(): void {
    console.log(this.movieForm.value);
    this.movieService.createMovie(this.movieForm.value).subscribe({
      next: () => {
        this.loadMovies();
        this.movieForm.reset();
        this.toggleCreateForm(); // Cacher le formulaire après la soumission
      },
      error: error => {
        console.error('Error creating movie:', error);
      }
    });
  }

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
    this.selectedMovie = {...movie};
  }

  submitUpdate(): void {
    this.movieService.updateMovie(this.selectedMovie.id, this.selectedMovie).subscribe({
      next: () => {
        this.loadMovies();
        this.selectedMovie = null;
      },
      error: error => {
        console.error('Error updating movie:', error);
      }
    });
  }

  cancelUpdate(): void {
    this.selectedMovie = null;
  }

  deleteMovie(id: string): void {
    this.movieService.deleteMovie(id).subscribe(() => {
      this.loadMovies();
    });
  }

  // Fonction pour basculer la visibilité du formulaire
  toggleCreateForm() {
    this.createFormVisible = !this.createFormVisible;
  }
}
