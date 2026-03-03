import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

import { GameRequest, GameResponse } from '../../models/game';
import { RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
export class GameService
{
  private http = inject(HttpClient);
  private readonly baseUrl = 'https://speedrun-csce548.fly.dev/games'; // Check if this should be /games

  create(game: GameRequest): Promise<GameResponse> {
    return firstValueFrom(this.http.post<GameResponse>(this.baseUrl, game));
  }

  getAll(): Promise<GameResponse[]> {
    return firstValueFrom(this.http.get<GameResponse[]>(this.baseUrl));
  }

  getById(id: number): Promise<GameResponse> {
    return firstValueFrom(this.http.get<GameResponse>(`${this.baseUrl}/${id}`));
  }

  getCategories(id: number): Promise<string[]> {
    return firstValueFrom(this.http.get<string[]>(`${this.baseUrl}/${id}/categories`));
  }

  getLeaderboard(id: number, category: string): Promise<RunResponse[]> {
    const params = new HttpParams().set('category', category);
    return firstValueFrom(
      this.http.get<RunResponse[]>(`${this.baseUrl}/${id}/leaderboard`, { params })
    );
  }

  update(id: number, game: GameRequest): Promise<GameResponse> {
    return firstValueFrom(this.http.put<GameResponse>(`${this.baseUrl}/${id}`, game));
  }

  delete(id: number): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`));
  }
}