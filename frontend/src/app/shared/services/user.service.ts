import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {User} from "../../user/model/User";
import jwt_decode from "jwt-decode";
import {TokenPayload} from "../model/token";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

const REFRESH_TOKEN = "rt";
const ACCESS_TOKEN = "at";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor(private readonly http: HttpClient, private router: Router) {
    this.setIsLoggedIn();
  }

  logIn(username: string, password: string): Observable<any> {
    return this.http.post<any>(environment.apiUrl + "auth/login", {login: username, password: password})
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
    this.router.navigate(['/']);
  }

  register(user: User): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    // @ts-ignore
    return this.http.post<any>(environment.apiUrl + "registration/register", user, {headers, responseType: 'text'});
  }

  refreshTokens() {
    const rt = this.getRefreshToken();

    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Authorization': `Bearer ${rt}`,
      'skip': 'true'
    });

    console.log("refreshing tokens");

    return this.http.get<any>(environment.apiUrl + "auth/refresh_token", {headers})
      .pipe(map(
        data => {
          const rt = data.refresh_token;
          const at = data.access_token;
          this.saveTokens(rt, at);
        }
      ));
  }

  isTokenValid(token: string): boolean {
    const rtPayload = jwt_decode<TokenPayload>(token);
    const currentTime = new Date().getTime();
    return rtPayload.exp * 1000 - currentTime > 0;
  }

  getUserRole(): string | undefined{
    const at = this.getAccessToken();
    if (at == null) {
      return at
    }
    const atPayload = jwt_decode<TokenPayload>(at);
    return atPayload.roles[0];
  }

  getAccessToken(): string | undefined {
    return (
      localStorage.getItem(ACCESS_TOKEN) ??
      sessionStorage.getItem(ACCESS_TOKEN) ??
      undefined
    );
  }

  getRefreshToken(): string | undefined {
    return (
      localStorage.getItem(REFRESH_TOKEN) ??
      sessionStorage.getItem(REFRESH_TOKEN) ??
      undefined
    );
  }

  private setIsLoggedIn(){
    const rt = this.getRefreshToken();

    if (!rt){
     this.isLoggedIn = false;
     return;
    }

    this.isLoggedIn = this.isTokenValid(rt);
  }

  private saveTokens(refreshToken: string, accessToken: string): void {
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
    localStorage.setItem(ACCESS_TOKEN, accessToken);
  }
}
