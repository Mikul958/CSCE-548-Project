import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthorRequest, AuthorResponse } from '../../models/author';
import { RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
export class AuthorService
{
  private http = inject(HttpClient);

  private readonly baseUrl = 'https://speedrun-csce548.fly.dev/authors';

  createAuthor(author: AuthorRequest): Observable<AuthorResponse> {
    return this.http.post<AuthorResponse>(this.baseUrl, author);
  }

  getAllAuthors(): Observable<AuthorResponse[]> {
    return this.http.get<AuthorResponse[]>(this.baseUrl);
  }

  getAuthorById(id: number): Observable<AuthorResponse> {
    return this.http.get<AuthorResponse>(`${this.baseUrl}/${id}`);
  }

  getAuthorHistory(id: number): Observable<RunResponse[]> {
    return this.http.get<RunResponse[]>(`${this.baseUrl}/${id}/history`);
  }

  updateAuthor(id: number, author: AuthorRequest): Observable<AuthorResponse> {
    return this.http.put<AuthorResponse>(`${this.baseUrl}/${id}`, author);
  }

  deleteAuthor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}