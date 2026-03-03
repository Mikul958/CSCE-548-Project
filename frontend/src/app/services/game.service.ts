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

  createGame(game: GameRequest): Promise<GameResponse> {
    return firstValueFrom(this.http.post<GameResponse>(this.baseUrl, game));
  }

  getAllGames(): Promise<GameResponse[]> {
    return firstValueFrom(this.http.get<GameResponse[]>(this.baseUrl));
  }

  getGameById(id: number): Promise<GameResponse> {
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

  updateGame(id: number, game: GameRequest): Promise<GameResponse> {
    return firstValueFrom(this.http.put<GameResponse>(`${this.baseUrl}/${id}`, game));
  }

  deleteGame(id: number): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`));
  }
}