import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GameService } from '../services/game.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';

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
  templateUrl: './create-game.html',
  styleUrl: './create-game.scss'
})
export class CreateGameComponent {
  private fb = inject(FormBuilder);
  private gameService = inject(GameService);
  private router = inject(Router);
  private dialogRef = inject(MatDialogRef<CreateGameComponent>);

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

  async onSubmit() {
    if (this.gameForm.invalid)
      return;

    this.loading.set(true);
    try {
      const newGame = this.gameForm.value as any;
      await this.gameService.createGame(newGame);
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