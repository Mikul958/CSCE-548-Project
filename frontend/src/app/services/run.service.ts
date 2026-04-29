import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';
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
  private readonly baseUrl = environment.baseUrl + '/runs';

  formatTime(ms: number): string {
    const milliseconds = ms % 1000;
    const totalSeconds = Math.floor(ms / 1000);
    const seconds = totalSeconds % 60;
    const totalMinutes = Math.floor(totalSeconds / 60);
    const minutes = totalMinutes % 60;
    const hours = Math.floor(totalMinutes / 60);

    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}.${milliseconds.toString().padStart(3, '0')}`;
  }
  
  createRun(run: RunRequest): Promise<RunResponse> {
    return firstValueFrom(this.http.post<RunResponse>(this.baseUrl, run));
  }

  getAllRuns(): Promise<RunResponse[]> {
    return firstValueFrom(this.http.get<RunResponse[]>(this.baseUrl));
  }

  getRunById(id: number): Promise<RunResponse> {
    return firstValueFrom(this.http.get<RunResponse>(`${this.baseUrl}/${id}`));
  }

  updateRun(id: number, run: RunRequest): Promise<RunResponse> {
    return firstValueFrom(this.http.put<RunResponse>(`${this.baseUrl}/${id}`, run));
  }

  deleteRun(id: number): Promise<void> {
    return firstValueFrom(this.http.delete<void>(`${this.baseUrl}/${id}`));
  }
}