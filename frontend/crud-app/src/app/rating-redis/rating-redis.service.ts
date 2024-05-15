import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class RatingRedisService {
  private apiUrl = 'http://localhost:8080/redis/ratings';

  constructor(private http: HttpClient) {
  }

  createRating(rating: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, rating);
  }
  searchRating(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getRatings(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/paged?page=${page}&size=${size}`).pipe(
      map(response => {
        return response;
      })
    );
  }

  updateRating(id: string, rating: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, rating);
  }

  deleteRating(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

}
