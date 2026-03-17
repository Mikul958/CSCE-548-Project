import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthorService } from '../../services/author.service';
import { GameService } from '../../services/game.service';
import { RunService } from '../../services/run.service';
import { RunRequest } from '../../../models/run';

@Component({
  selector: 'app-create-run',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, 
    MatInputModule, MatButtonModule, MatAutocompleteModule, MatCheckboxModule
  ],
  templateUrl: './run-dialog.html',
  styleUrl: './run-dialog.scss'
})
export class RunDialogComponent implements OnInit {
  private authorService = inject(AuthorService);
  private gameService = inject(GameService);
  private runService = inject(RunService);

  dialogRef = inject(MatDialogRef<RunDialogComponent>);
  public data = inject(MAT_DIALOG_DATA);
  private fb = inject(FormBuilder);

  isEditMode = signal(false);
  loading = signal(false);
  games = signal<any[]>([]);
  authors = signal<any[]>([]);

  runForm = this.fb.group({
    gameId: [null as number | null, [Validators.required]],
    authorId: [null as number | null, [Validators.required]],
    category: ['', [Validators.required]],
    time: ['', [Validators.required, Validators.pattern(/^(\d+:)?([0-5]?\d:)?([0-5]?\d)\.(\d{3})$/)]],
    videoUrl: ['', [Validators.required]],
    setDate: [new Date().toISOString().split('T')[0], [Validators.required]],
    verified: [false]
  });

  // Filtering logic for the Autocomplete
  filteredGames = computed(() => {
    const search = this.gameSearch().toLowerCase();
    return this.games().filter(g => g.title.toLowerCase().includes(search));
  });

  filteredAuthors = computed(() => {
    const search = this.authorSearch().toLowerCase();
    return this.authors().filter(a => a.displayName.toLowerCase().includes(search));
  });

  gameSearch = signal('');
  authorSearch = signal('');

  async ngOnInit() {
    this.loading.set(true);
    try {
      const [g, a] = await Promise.all([
        this.gameService.getAllGames(),
        this.authorService.getAllAuthors()
      ]);
      this.games.set(g);
      this.authors.set(a);

      if (this.data && this.data.run) {
        this.isEditMode.set(true);
        const run = this.data.run;

        // Ensure we use the IDs from the nested objects if gameId/authorId aren't flat
        const gameId = run.gameId || run.game?.id;
        const authorId = run.authorId || run.author?.id;

        this.runForm.patchValue({
          gameId: gameId,
          authorId: authorId,
          category: run.category,
          time: this.runService.formatTime(run.timeMilliseconds),
          videoUrl: run.videoUrl,
          setDate: run.setDate.split('T')[0],
          verified: run.verified
        });

        // Crucial: Set the search signals to the actual Titles/Names 
        // so the inputs aren't blank on load
        this.gameSearch.set(this.displayGame(gameId));
        this.authorSearch.set(this.displayAuthor(authorId));
      }
    } finally {
      this.loading.set(false);
    }
  }

  displayGame(gameId: number): string {
    return this.games().find(g => g.id === gameId)?.title || '';
  }

  displayAuthor(authorId: number): string {
    return this.authors().find(a => a.id === authorId)?.displayName || '';
  }

  async onSubmit() {
    if (this.runForm.invalid)
      return;
    this.loading.set(true);

    const formValue = this.runForm.value;
    const runRequest: RunRequest = {
      ...formValue,
      timeMilliseconds: this.parseTimeToMs(formValue.time!)
    } as any;

    try {
      if (this.isEditMode()) {
        await this.runService.updateRun(this.data.run.id, runRequest);
      } else {
        await this.runService.createRun(runRequest);
      }
      this.dialogRef.close(true);
    } catch (err) {
      alert("Error saving run.");
    } finally {
      this.loading.set(false);
    }
  }

  private parseTimeToMs(timeStr: string): number {
    const parts = timeStr.split(/[:.]/);
    let hours = 0, mins = 0, secs = 0, ms = 0;

    if (parts.length === 4) { [hours, mins, secs, ms] = parts.map(Number); }
    else if (parts.length === 3) { [mins, secs, ms] = parts.map(Number); }
    else { [secs, ms] = parts.map(Number); }

    return (hours * 3600000) + (mins * 60000) + (secs * 1000) + ms;
  }

  onCancel() {
    this.dialogRef.close(false);
  }
}