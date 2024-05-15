import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})

export class RatingService {
  private apiUrl = 'http://localhost:8080/mongodb/ratings';

  constructor(private http: HttpClient) { }

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

  createRating(rating: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, rating);
  }

  deleteRating(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
