import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { AuthorService } from '../services/author.service';

@Component({
  selector: 'app-create-author',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    MatDialogModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule
  ],
  templateUrl: './create-author.html',
  styleUrl: './create-author.scss'
})
export class CreateAuthorComponent {
  private fb = inject(FormBuilder);
  private authorService = inject(AuthorService);
  private dialogRef = inject(MatDialogRef<CreateAuthorComponent>);

  loading = signal(false);
  error = signal<string | null>(null);

  authorForm = this.fb.group({
    username: ['', [Validators.required]],
    displayName: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    profilePictureUrl: ['']
  });

  async onSubmit() {
    if (this.authorForm.invalid) return;

    this.loading.set(true);
    this.error.set(null);

    // Merge form values with the auto-generated date
    const newAuthor = {
      ...this.authorForm.value,
      createDate: new Date().toISOString() // Populating with today's date
    } as any;

    try {
      await this.authorService.createAuthor(newAuthor);
      this.dialogRef.close(true);
    } catch (err) {
      this.error.set('Failed to create author. Username might be taken.');
    } finally {
      this.loading.set(false);
    }
  }

  onCancel() {
    this.dialogRef.close(false);
  }
}