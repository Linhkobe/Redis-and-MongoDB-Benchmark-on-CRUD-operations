import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MovieRedisService {
  private apiUrl = 'http://localhost:8080/redis/movies';  // Replace with your API endpoint

  constructor(private http: HttpClient) { }

  updateMovie(id: string, movie: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, movie);
  }


  deleteMovie(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  getMovies(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/paged?page=${page}&size=${size}`);
  }


  searchMovie(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getAllMovies(): Observable<any>{
    return this.http.get(`${this.apiUrl}/`);
  }

  //creer un movie avec le formulaire create
  createMovie(movie: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, movie);
  }

  getMovieByTitle(movieTitle: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/by-title`, { params: { title: movieTitle } });
  }



}
