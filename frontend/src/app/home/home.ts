import { Component, effect, inject, OnInit, signal, viewChild, AfterViewInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Router, RouterLink } from '@angular/router';

import { AuthorService } from '../services/author.service';
import { GameService } from '../services/game.service';
import { RunService } from '../services/run.service';

import { AuthorDialogComponent } from '../dialogs/author-dialog/author-dialog';
import { GameDialogComponent } from '../dialogs/game-dialog/game-dialog';
import { RunDialogComponent } from '../dialogs/run-dialog/run-dialog';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class HomeComponent implements OnInit {
  private authorService = inject(AuthorService);
  private gameService = inject(GameService);
  runService = inject(RunService);

  private router = inject(Router);
  private dialog = inject(MatDialog);

  // DataSources for Material Tables
  authorDataSource = new MatTableDataSource<any>([]);
  gameDataSource = new MatTableDataSource<any>([]);
  runDataSource = new MatTableDataSource<any>([]);

  // ViewChild using the modern signal-based query
  authorPaginator = viewChild('authorPaging', { read: MatPaginator });
  gamePaginator = viewChild('gamePaging', { read: MatPaginator });
  runPaginator = viewChild('runPaging', { read: MatPaginator });

  loading = signal(true);

  constructor() {
    // Effect runs whenever the signals inside it change
    effect(() => {
      const aPaginator = this.authorPaginator();
      const gPaginator = this.gamePaginator();
      const rPaginator = this.runPaginator();

      // Once loading is false and paginators are available in the DOM
      if (!this.loading() && aPaginator && gPaginator && rPaginator) {
        this.authorDataSource.paginator = aPaginator;
        this.gameDataSource.paginator = gPaginator;
        this.runDataSource.paginator = rPaginator;
      }
    });
  }

  async ngOnInit() {
    try {
      // Fetch data using the Promises we set up
      const [authors, games, runs] = await Promise.all([
        this.authorService.getAllAuthors(),
        this.gameService.getAllGames(),
        this.runService.getAllRuns()
      ]);

      this.authorDataSource.data = authors;
      this.gameDataSource.data = games;
      this.runDataSource.data = runs;

      // Link paginators
      this.authorDataSource.paginator = this.authorPaginator();
      this.gameDataSource.paginator = this.gamePaginator()!;
      this.runDataSource.paginator = this.runPaginator()!;
    } catch (error) {
      console.error('Error loading home data', error);
    } finally {
      this.loading.set(false);
    }
  }

  // Inside HomeComponent class:
  openCreateAuthorPopup() {
    const dialogRef = this.dialog.open(AuthorDialogComponent, {
      width: '500px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.refreshAuthors(); // Implement similar to refreshGames()
      }
    });
  }

  async refreshAuthors() {
    const authors = await this.authorService.getAllAuthors();
    this.authorDataSource.data = authors;
  }

  openCreateGamePopup() {
    const dialogRef = this.dialog.open(GameDialogComponent, {
      width: '600px',
      disableClose: true // Prevents closing by clicking outside during save
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        // Refresh the games list if a game was actually created
        this.refreshGames();
      }
    });
  }

  async refreshGames() {
    const games = await this.gameService.getAllGames();
    this.gameDataSource.data = games;
  }

  openCreateRunPopup() {
    const dialogRef = this.dialog.open(RunDialogComponent, {
      width: '650px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        // Refresh the runs list to show the new submission
        this.refreshRuns();
      }
    });
  }

  async refreshRuns() {
    const runs = await this.runService.getAllRuns();
    this.runDataSource.data = runs;
  }
}
