import { Routes } from '@angular/router';

import { HistoryComponent } from './history/history';
import { HomeComponent } from './home/home';
import { LeaderboardComponent } from './leaderboard/leaderboard';
import { RunComponent } from './run/run';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'Home'
    },
    {
        path: 'game',
        component: LeaderboardComponent,
        title: 'Leaderboard'
    },
    {
        path: 'author',
        component: HistoryComponent,
        title: 'Author History'
    },
    {
        path: 'run',
        component: RunComponent,
        title: 'Run'
    }
];
