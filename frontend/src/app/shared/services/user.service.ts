import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable, Subscription} from "rxjs";
import {User} from "../../user/model/User";
import jwt_decode from "jwt-decode";
import {TokenPayload} from "../model/token";

const REFRESH_TOKEN = "rt";
const ACCESS_TOKEN = "at";

const authApiPrefix = 'http://localhost:8080/auth/login';
const registrationApiPrefix = 'http://localhost:8080/registration/register';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor(private readonly http: HttpClient) {
    this.setIsLoggedIn();
  }

  logIn(username: string, password: string): Observable<any> {
    return this.http.post<any>(authApiPrefix, {login: username, password: password})
      .pipe(map(data => {
        const rt = data.refresh_token;
        const at = data.access_token;
        this.saveTokens(rt, at);
        this.isLoggedIn = true;
      }));
  }

  logout(): void {
    this.isLoggedIn = false;
    localStorage.removeItem(REFRESH_TOKEN);
    localStorage.removeItem(ACCESS_TOKEN);
    location.reload()
  }

  register(user: User): Observable<any> {
    return this.http.post<any>(registrationApiPrefix, user);
  }

  private saveTokens(refreshToken: string, accessToken: string): void {
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
    sessionStorage.setItem(ACCESS_TOKEN, accessToken);
  }

  private setIsLoggedIn(){
    const rt = this.getRefreshToken();

    if (!rt) return;

    this.isLoggedIn = this.isTokenValid(rt);
  }

  private getRefreshToken(): string | undefined {
    return (
      localStorage.getItem(REFRESH_TOKEN) ??
      sessionStorage.getItem(REFRESH_TOKEN) ??
      undefined
    );
  }

  private getAccessToken(): string | undefined {
    return (
      localStorage.getItem(ACCESS_TOKEN) ??
      sessionStorage.getItem(ACCESS_TOKEN) ??
      undefined
    );
  }

  private isTokenValid(token: string): boolean {
    const rtPayload = jwt_decode<TokenPayload>(token);
    const currentTime = new Date().getTime();
    return rtPayload.exp * 1000 - currentTime > 0;
  }

}
