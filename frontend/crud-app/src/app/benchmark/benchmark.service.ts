import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { catchError, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BenchmarkService {

  private apiUrl = 'http://localhost:8080/api/benchmark';

  constructor(private http: HttpClient) { }

  createMovies(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.post(`${this.apiUrl}/createMovies`, {}, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }

  createRatings(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.post(`${this.apiUrl}/createRatings`, {}, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }

  createUsers(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.post(`${this.apiUrl}/createUsers`, {}, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }

  updateMovies(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    return this.http.patch(`${this.apiUrl}/updateMovies`, { count, runs }, { params })
      .pipe(
        tap(response => console.log('Update response:', response)),
        catchError(error => {
          console.error('Error during update:', error);
          throw error;
        })
      );
  }
  updateRatings(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    return this.http.patch(`${this.apiUrl}/updateRatings`, { count, runs }, { params })
      .pipe(
        tap(response => console.log('Update response:', response)),
        catchError(error => {
          console.error('Error during update:', error);
          throw error;
        })
      );
  }
  updateUsers(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    return this.http.patch(`${this.apiUrl}/updateUsers`, { count, runs }, { params })
      .pipe(
        tap(response => console.log('Update response:', response)),
        catchError(error => {
          console.error('Error during update:', error);
          throw error;
        })
      );
  }

  findMovies(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.get(`${this.apiUrl}/findMovies`, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }

  findRatings(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.get(`${this.apiUrl}/findRatings`, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }

  findUsers(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.get(`${this.apiUrl}/findUsers`, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }
  deleteMovies(count: number, runs: number): Observable<any> {
    let params = new HttpParams().set('count', count.toString()).set('runs', runs.toString());
    console.log(`Sending request with Count: ${count}, Runs: ${runs}`);
    return this.http.delete(`${this.apiUrl}/deleteMovies`, { params: params })
      .pipe(
        tap((response: any) => console.log('Response:', response)),
        catchError(error => {
          console.error('Error:', error);
          throw error;
        })
      );
  }
}
