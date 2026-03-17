import { Component, inject, OnInit, signal, viewChild, effect } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon'
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';

import { AuthorDialogComponent } from '../dialogs/author-dialog/author-dialog';
import { AuthorService } from '../services/author.service';
import { RunService } from '../services/run.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatTableModule, MatPaginatorModule, RouterLink],
  templateUrl: './history.html',
  styleUrls: ['./history.scss']
})
export class HistoryComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private dialog = inject(MatDialog);

  private authorService = inject(AuthorService);
  runService = inject(RunService);

  // Author State
  author = signal<any>(null);
  loading = signal(true);
  
  // Table State
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns: string[] = ['game', 'category', 'time', 'date'];
  paginator = viewChild(MatPaginator);

  constructor() {
    // Link paginator once the @if(loading()) block resolves
    effect(() => {
      const p = this.paginator();
      if (p) this.dataSource.paginator = p;
    });
  }

  async ngOnInit() {
    this.route.queryParamMap.subscribe(async (params) => {
      const id = Number(params.get('id'));
      if (!id) return;

      this.loading.set(true);
      try {
        // Fetch both the profile info and the run history
        const [profile, history] = await Promise.all([
          this.authorService.getAuthorById(id),
          this.authorService.getAuthorHistory(id)
        ]);

        this.author.set(profile);
        this.dataSource.data = history;
      } catch (error) {
        console.error('Error fetching history:', error);
      } finally {
        this.loading.set(false);
      }
    });
  }

  openEditAuthorPopup() {
    const dialogRef = this.dialog.open(AuthorDialogComponent, {
      width: '500px',
      data: { author: this.author() }
    });

    dialogRef.afterClosed().subscribe(success => {
      if (success) {
        this.refreshAuthor();
      }
    });
  }

  async refreshAuthor() {
    const updatedAuthor = await this.authorService.getAuthorById(this.author().id);
    this.author.set(updatedAuthor);
  }
}
