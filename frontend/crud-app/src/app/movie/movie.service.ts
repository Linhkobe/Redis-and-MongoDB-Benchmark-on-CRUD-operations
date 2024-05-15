import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = 'http://localhost:8080/mongodb/movies';  // Replace with your API endpoint

  constructor(private http: HttpClient) { }

  searchMovie(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getMovies(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/paged?page=${page}&size=${size}`);
  }

  createMovie(movieData: any): Observable<any> { // Ajoutez la méthode createMovie
    return this.http.post(`${this.apiUrl}/create`, movieData);
  }

  updateMovie(id: string, movie: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, movie);
  }

  deleteMovie(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
