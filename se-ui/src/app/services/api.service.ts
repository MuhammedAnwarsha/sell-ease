// src/app/services/api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API } from '../apiUrl';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const url = `${API.BASE_URL}${API.AUTH.LOGIN}`;
    return this.http.post(url, { email, password });
  }

  signup(fullname: string, email: string, phone: string, password: string): Observable<any> {
    const url = `${API.BASE_URL}${API.AUTH.SIGNUP}`;
    return this.http.post(url, { fullname, email, phone, password });
  }

  getDashboardData(token:string): Observable<any>{
    const url = `${API.BASE_URL}${API.DASHBOARD.GETSELLERDASHBOARD}`;
    const headers = new HttpHeaders({
        'Authorization':`Bearer ${token}`
    });
    return this.http.get(url,{headers});
  }

  getUserProfile(): Observable<any>{
    const url = `${API.BASE_URL}${API.DASHBOARD.USERPROFILE}`
    const token = localStorage.getItem('jwt');
    const headers = new HttpHeaders({
        'Authorization':`Bearer ${token}`
    });
    return this.http.get(url,{headers});
  }

}
