import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {User} from "../../user/model/User";
import jwt_decode from "jwt-decode";
import {TokenPayload} from "../model/token";
import {Router} from "@angular/router";

const REFRESH_TOKEN = "rt";
const ACCESS_TOKEN = "at";

const authApiPrefix = 'http://localhost:8080/auth';
const registrationApiPrefix = 'http://localhost:8080/registration/register';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor(private readonly http: HttpClient, private router: Router) {
    this.setIsLoggedIn();
  }

  logIn(username: string, password: string): Observable<any> {
    return this.http.post<any>(authApiPrefix + "/login", {login: username, password: password})
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
    return this.http.post<any>(registrationApiPrefix, user, {headers, responseType: 'text'});
  }

  refreshTokens() {
    const rt = this.getRefreshToken();

    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Authorization': `Bearer ${rt}`
    });

    return this.http.get<any>(authApiPrefix + "/refresh_token", {headers})
      .pipe(map(
        data => {
          const rt = data.refresh_token;
          const at = data.access_token;
          this.saveTokens(rt, at);
          this.isLoggedIn = true;
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
    console.log(atPayload);
    return atPayload.roles[0];
  }

  getAccessToken(): string | undefined {
    return (
      localStorage.getItem(ACCESS_TOKEN) ??
      sessionStorage.getItem(ACCESS_TOKEN) ??
      undefined
    );
  }

  private getRefreshToken(): string | undefined {
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
