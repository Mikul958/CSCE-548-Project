import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';

import { GameService } from '../../services/game.service';

@Component({
  selector: 'app-create-game',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule,
    MatDialogModule
  ],
  templateUrl: './game-dialog.html',
  styleUrl: './game-dialog.scss'
})
export class GameDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  private gameService = inject(GameService);
  private router = inject(Router);
  private dialogRef = inject(MatDialogRef<GameDialogComponent>);

  // Inject the data passed from the opening component
  public data = inject(MAT_DIALOG_DATA);

  isEditMode = signal(false);
  loading = signal(false);
  error = signal<string | null>(null);

  // Define the form structure with validation
  gameForm = this.fb.group({
    title: ['', [Validators.required, Validators.minLength(2)]],
    description: ['', [Validators.required]],
    thumbnailUrl: [''],
    releaseDate: ['', [Validators.required]],
    developer: ['', [Validators.required]],
    publisher: ['', [Validators.required]]
  });

  ngOnInit() {
    // Check if data was passed in (means we're updating instead of creating) and patch it in if so
    if (this.data && this.data.game) {
      this.isEditMode.set(true);
      this.gameForm.patchValue(this.data.game);
    }
  }

  async onSubmit() {
    if (this.gameForm.invalid)
      return;

    this.loading.set(true);
    const gameData = this.gameForm.value as any;

    try {
      if (this.isEditMode())
          await this.gameService.updateGame(this.data.game.id, gameData);
      else
        await this.gameService.createGame(gameData);
      this.dialogRef.close(true); 
    } catch (err) {
      this.error.set('Failed to create game.');
    } finally {
      this.loading.set(false);
    }
  }

  onCancel() {
    this.dialogRef.close(false);
  }
}