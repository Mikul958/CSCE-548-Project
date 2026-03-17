import { Component, inject, OnInit, signal, viewChild, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon'
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

import { GameDialogComponent } from '../dialogs/game-dialog/game-dialog';
import { GameService } from '../services/game.service';
import { RunService } from '../services/run.service';

@Component({
  selector: 'app-leaderboard',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatTableModule, MatPaginatorModule, RouterLink],
  templateUrl: './leaderboard.html',
  styleUrl: './leaderboard.scss'
})
export class LeaderboardComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private dialog = inject(MatDialog);

  private gameService = inject(GameService);
  runService = inject(RunService);

  // State Signals
  game = signal<any>(null);
  categories = signal<string[]>([]);
  selectedCategory = signal<string>('');
  loading = signal(true);
  loadingBoard = signal(false);

  // Caching Map: Key = Category Name, Value = RunResponse[]
  private leaderboardCache = new Map<string, any[]>();

  // Table Setup
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns = ['rank', 'player', 'time', 'date'];
  paginator = viewChild(MatPaginator);

  constructor() {
    // Automatically fetch new data when the category signal changes
    effect(async () => {
      const category = this.selectedCategory();
      const gameId = this.game()?.id;
      
      if (!category || !gameId) return;

      if (this.leaderboardCache.has(category)) {
        this.dataSource.data = this.leaderboardCache.get(category)!;
      } else {
        this.loadingBoard.set(true);
        try {
          const data = await this.gameService.getLeaderboard(gameId, category);
          this.leaderboardCache.set(category, data);
          this.dataSource.data = data;
        } finally {
          this.loadingBoard.set(false);
        }
      }
      
      // Re-link paginator after data load
      if (this.paginator()) {
        this.dataSource.paginator = this.paginator()!;
      }
    });
  }

  async ngOnInit() {
    const id = Number(this.route.snapshot.queryParamMap.get('id'));
    if (!id) return;

    try {
      const [gameData, catData] = await Promise.all([
        this.gameService.getGameById(id),
        this.gameService.getCategories(id)
      ]);

      this.game.set(gameData);
      this.categories.set(catData);
      
      if (catData.length > 0) {
        this.selectedCategory.set(catData[0]); // Default to first category
      }
    } catch (err) {
      console.error('Failed to load leaderboard base data', err);
    } finally {
      this.loading.set(false);
    }
  }

  openEditGamePopup() {
    const dialogRef = this.dialog.open(GameDialogComponent, {
      width: '600px',
      data: { game: this.game() } // Pass the current game object here
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Re-fetch game details to show updated info on the page
        this.refreshGame(); 
      }
    });
  }

  async refreshGame() {
    const updatedGame = await this.gameService.getGameById(this.game().id);
    this.game.set(updatedGame);
  }
}