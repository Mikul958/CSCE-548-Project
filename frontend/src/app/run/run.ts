import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon'

import { RunDialogComponent } from '../dialogs/run-dialog/run-dialog';
import { RunResponse } from '../../models/run';
import { RunService } from '../services/run.service';

@Component({
  selector: 'app-run',
  standalone: true,
  imports: [CommonModule, MatIconModule, RouterLink],
  templateUrl: './run.html',
  styleUrl: './run.scss',
})
export class RunComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private dialog = inject(MatDialog);

  runService = inject(RunService);

  // Define these as Signals
  run = signal<any>(null);
  loading = signal(true);
  error = signal<string | undefined>(undefined);

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(async (params) => {
      const id = Number(params.get('id'));

      if (!id || isNaN(id)) {
        this.error.set('Invalid or missing run ID.');
        this.loading.set(false);
        return;
      }

      try {
        const data = await this.runService.getRunById(id);
        this.run.set(data);
        console.log(this.run())
      } catch (err) {
        this.error.set('Run not found.');
      } finally {
        this.loading.set(false);
      }
    });
  }

  openEditRunPopup() {
    const dialogRef = this.dialog.open(RunDialogComponent, {
      width: '650px',
      data: { run: this.run() } // Pass the current run signal data
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result === true) {
        // Re-fetch this specific run to show updated info
        const id = this.run().id;
        try {
          const updatedRun = await this.runService.getRunById(id);
          this.run.set(updatedRun); // Update the signal to refresh the UI
        } catch (err) {
          console.error("Error refreshing run data", err);
        }
      }
    });
  }
}
