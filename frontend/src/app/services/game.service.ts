import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { GameRequest, GameResponse } from '../../models/game';
import { RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private http = inject(HttpClient);

  private readonly baseUrl = 'http://localhost:8080/games';

  // CREATE
  create(game: GameRequest): Observable<GameResponse> {
    return this.http.post<GameResponse>(this.baseUrl, game);
  }

  // READ - get all
  getAll(): Observable<GameResponse[]> {
    return this.http.get<GameResponse[]>(this.baseUrl);
  }

  // READ - by id
  getById(id: number): Observable<GameResponse> {
    return this.http.get<GameResponse>(`${this.baseUrl}/${id}`);
  }

  // READ - categories for a game
  getCategories(id: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/${id}/categories`);
  }

  // READ - leaderboard (with query param)
  getLeaderboard(id: number, category: string): Observable<RunResponse[]> {
    const params = new HttpParams().set('category', category);

    return this.http.get<RunResponse[]>(
      `${this.baseUrl}/${id}/leaderboard`,
      { params }
    );
  }

  // UPDATE
  update(id: number, game: GameRequest): Observable<GameResponse> {
    return this.http.put<GameResponse>(`${this.baseUrl}/${id}`, game);
  }

  // DELETE
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}