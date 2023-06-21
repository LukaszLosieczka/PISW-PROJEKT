export interface TokenPayload {
  exp: number
  iss: string
  sub: string
  roles: string[]
}
