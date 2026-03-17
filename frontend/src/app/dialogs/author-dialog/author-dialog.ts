import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { AuthorService } from '../../services/author.service';

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
  templateUrl: './author-dialog.html',
  styleUrl: './author-dialog.scss'
})
export class AuthorDialogComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<AuthorDialogComponent>);
  public data = inject(MAT_DIALOG_DATA);

  private authorService = inject(AuthorService);

  isEditMode = signal(false);
  loading = signal(false);
  error = signal<string | null>(null);

  authorForm = this.fb.group({
    username: ['', [Validators.required]],
    displayName: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    profilePictureUrl: ['']
  });

  ngOnInit() {
    if (this.data && this.data.author) {
      this.isEditMode.set(true);
      this.authorForm.patchValue(this.data.author);
    }
  }

  async onSubmit() {
    if (this.authorForm.invalid)
      return;

    this.loading.set(true);
    this.error.set(null);

    const formValue = this.authorForm.value;
    try {
      if (this.isEditMode()) {
        // If updating, pass the author's create date back in
        const authorUpdate = {
          ...formValue,
          createDate: this.data.author.createDate
        } as any;
        await this.authorService.updateAuthor(this.data.author.id, authorUpdate);
      } else {
        // If creating, auto-generate the current date and pass in
        const newAuthor = {
          ...formValue,
          createDate: new Date().toISOString()
        } as any;
        await this.authorService.createAuthor(newAuthor);
      }
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