import { AuthorResponse } from './author'
import { GameResponse } from './game'

export interface RunRequest {
    gameId: number,
    authorId: number,
    category: string,
    timeMilliseconds: number,
    videoUrl: string,
    setDate: string,
    verified: boolean
}

export interface RunResponse {
    id: number,
    game: GameResponse,
    author: AuthorResponse,
    category: string,
    timeMilliseconds: number,
    videoUrl: string,
    setDate: string,
    verified: boolean
}