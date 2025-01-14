export interface Document {
    id: number,
    submitterId: string,
    name: string,
    description: string,
    fileId: string,
    fileBase64: string,
    folderId: string,
}

export interface User {
    name: string,
    username: string,
    email: string,
    password: string,
    role: string,
}
