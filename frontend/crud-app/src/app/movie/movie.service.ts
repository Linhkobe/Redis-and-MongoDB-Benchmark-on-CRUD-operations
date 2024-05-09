// movie.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = 'http://localhost:8080/mongodb/movies';  // Replace with your API endpoint

  constructor(private http: HttpClient) { }

  getMovies(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  updateMovie(id: string, movie: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, movie);
  }

  deleteMovie(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}