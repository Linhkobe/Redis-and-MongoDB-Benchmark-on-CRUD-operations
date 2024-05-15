import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserRedisService {
  private apiUrl = 'http://localhost:8080/redis/users';  // Replace with your Redis API endpoint

  constructor(private http: HttpClient) { }

  createUser(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, user);
  }

  searchUser(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getUsers(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/paged?page=${page}&size=${size}`);
  }

  updateUser(id: string, user: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, user);
  }

  deleteUser(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
