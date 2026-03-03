import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { switchMap, map } from 'rxjs/operators';
import { of } from 'rxjs';

import { RunResponse } from '../../models/run';
import { RunService } from '../services/run.service';

@Component({
  selector: 'app-run',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './run.html',
  styleUrl: './run.scss',
})
export class RunComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private runService = inject(RunService);

  // Define these as Signals
  run = signal<RunResponse | undefined>(undefined);
  loading = signal(true);
  error = signal<string | undefined>(undefined);

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(async (params) => {
      const id = Number(params.get('id'));

      // Set values using the .set() method
      this.loading.set(true);
      this.error.set(undefined);
      this.run.set(undefined);

      if (!id || isNaN(id)) {
        this.error.set('Invalid or missing run ID.');
        this.loading.set(false);
        return;
      }

      try {
        const data = await this.runService.getById(id);
        this.run.set(data);
        console.log(this.run())
      } catch (err) {
        this.error.set('Run not found.');
      } finally {
        this.loading.set(false);
      }
    });
  }

  formatTime(ms: number): string {
    const totalSeconds = Math.floor(ms / 1000);
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;
    const milliseconds = ms % 1000;

    return `${minutes}:${seconds.toString().padStart(2, '0')}.${milliseconds
      .toString()
      .padStart(3, '0')}`;
  }
}
