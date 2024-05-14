import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserRedisService {
  private apiUrl = 'http://localhost:8080/redis/users';  // Replace with your Redis API endpoint



  searchUser(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }



}
