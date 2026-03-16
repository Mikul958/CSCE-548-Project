import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthorService } from '../services/author.service';
import { GameService } from '../services/game.service';
import { RunService } from '../services/run.service';
import { RunRequest } from '../../models/run';

@Component({
  selector: 'app-create-run',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, 
    MatInputModule, MatButtonModule, MatAutocompleteModule, MatCheckboxModule
  ],
  templateUrl: './create-run.html',
  styleUrl: './create-run.scss'
})
export class CreateRunComponent implements OnInit {
  private authorService = inject(AuthorService);
  private gameService = inject(GameService);
  private runService = inject(RunService);

  dialogRef = inject(MatDialogRef<CreateRunComponent>);
  private fb = inject(FormBuilder);

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
    try {
      const [g, a] = await Promise.all([
        this.gameService.getAllGames(),
        this.authorService.getAllAuthors()
      ]);
      this.games.set(g);
      this.authors.set(a);
    } catch (err) {
      console.error("Failed to load lookup data", err);
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

    const formValue = this.runForm.value;
    const runRequest: RunRequest = {
      ...formValue,
      timeMilliseconds: this.parseTimeToMs(formValue.time!)
    } as any;

    this.loading.set(true);
    try {
      await this.runService.createRun(runRequest as any);
      this.dialogRef.close(true);
    } catch (err) {
      alert("Failed to create run.");
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