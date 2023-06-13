import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable, Subscription} from "rxjs";

const REFRESH_TOKEN = "rt";
const ACCESS_TOKEN = "at";

const authApiPrefix = 'http://localhost:8080/auth/login';
const registrationApiPrefix = 'http://localhost:8080/registration/register';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor(private readonly http: HttpClient) {}

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

  register(): void {}

  private saveTokens(refreshToken: string, accessToken: string): void {
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
    sessionStorage.setItem(ACCESS_TOKEN, accessToken);
  }

}
