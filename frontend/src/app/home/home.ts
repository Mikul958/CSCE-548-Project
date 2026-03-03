import { Component, effect, inject, OnInit, signal, viewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Router, RouterLink } from '@angular/router';

import { AuthorService } from '../services/author.service';
import { GameService } from '../services/game.service';
import { RunService } from '../services/run.service';

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
}
