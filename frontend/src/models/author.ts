export interface AuthorRequest {
    username: string,
    displayName: string,
    password: string,
    profilePictureUrl: string,
    createDate: string
}

export interface AuthorResponse {
    id: number,
    username: string,
    displayName: string,
    profilePictureUrl: string,
    createDate: string
}