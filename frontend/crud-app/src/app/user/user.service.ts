import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/mongodb/users';  // Replace with your API endpoint

  constructor(private http: HttpClient) { }

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
