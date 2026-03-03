export interface GameRequest {
    title: string,
    description: string,
    thumbnailUrl: string,
    releaseDate: string,
    developer: string,
    publisher: string
}

export interface GameResponse {
    id: number,
    title: string,
    description: string,
    thumbnailUrl: string,
    releaseDate: string,
    developer: string,
    publisher: string
}