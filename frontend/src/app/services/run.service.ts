import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { RunRequest, RunResponse } from '../../models/run';

@Injectable({
  providedIn: 'root'
})
@Injectable({
  providedIn: 'root'
})
export class RunService
{
  private http = inject(HttpClient);
  private readonly baseUrl = 'https://speedrun-csce548.fly.dev/runs'; // Check if this should be /runs

  create(run: RunRequest): Promise<RunResponse> {
    return firstValueFrom(this.http.post<RunResponse>(this.baseUrl, run));
  }

  getAll(): Promise<RunResponse[]> {
    return firstValueFrom(this.http.get<RunResponse[]>(this.baseUrl));
  }

  getById(id: number): Promise<RunResponse> {
    return firstValueFrom(this.http.get<RunResponse>(`${this.baseUrl}/${id}`));
  }

  update(id: number, run: RunRequest): Promise<RunResponse> {
    return firstValueFrom(this.http.put<RunResponse>(`${this.baseUrl}/${id}`, run));
  }

  delete(id: number): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`));
  }
}