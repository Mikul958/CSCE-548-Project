import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { RunRequest, RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
export class RunService {

  private http = inject(HttpClient);

  private readonly baseUrl = 'http://localhost:8080/runs';

  // CREATE
  create(run: RunRequest): Observable<RunResponse> {
    return this.http.post<RunResponse>(this.baseUrl, run);
  }

  // READ - get all
  getAll(): Observable<RunResponse[]> {
    return this.http.get<RunResponse[]>(this.baseUrl);
  }

  // READ - by id
  getById(id: number): Observable<RunResponse> {
    return this.http.get<RunResponse>(`${this.baseUrl}/${id}`);
  }

  // UPDATE
  update(id: number, run: RunRequest): Observable<RunResponse> {
    return this.http.put<RunResponse>(`${this.baseUrl}/${id}`, run);
  }

  // DELETE
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}