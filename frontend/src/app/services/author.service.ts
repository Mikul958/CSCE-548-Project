import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { AuthorRequest, AuthorResponse } from '../../models/author';
import { RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
@Injectable({
  providedIn: 'root'
})
export class AuthorService
{
  private http = inject(HttpClient);
  private readonly baseUrl = 'https://speedrun-csce548.fly.dev/authors';

  createAuthor(author: AuthorRequest): Promise<AuthorResponse> {
    return firstValueFrom(this.http.post<AuthorResponse>(this.baseUrl, author));
  }

  getAllAuthors(): Promise<AuthorResponse[]> {
    return firstValueFrom(this.http.get<AuthorResponse[]>(this.baseUrl));
  }

  getAuthorById(id: number): Promise<AuthorResponse> {
    return firstValueFrom(this.http.get<AuthorResponse>(`${this.baseUrl}/${id}`));
  }

  getAuthorHistory(id: number): Promise<RunResponse[]> {
    return firstValueFrom(this.http.get<RunResponse[]>(`${this.baseUrl}/${id}/history`));
  }

  updateAuthor(id: number, author: AuthorRequest): Promise<AuthorResponse> {
    return firstValueFrom(this.http.put<AuthorResponse>(`${this.baseUrl}/${id}`, author));
  }

  deleteAuthor(id: number): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`));
  }
}