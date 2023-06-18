import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {UserService} from "../shared/services/user.service";
import {Router} from "@angular/router";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private userService: UserService,  private router: Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const isApiUrl = request.url.startsWith(environment.apiUrl);;
    const isLoggedIn = this.userService.isLoggedIn;
    const at = this.userService.getAccessToken()
    let isTokenValid = at && this.userService.isTokenValid(at);
    if(!isTokenValid) {
      this.userService.refreshTokens()
        .subscribe({
          next: () => {
            isTokenValid = true
          },
          error: error => {
            this.userService.logout();
            this.router.navigate(['/login']);
          }
        });
    }
    if (isLoggedIn && isApiUrl && isTokenValid) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${at}`
        }
      });
    }
    return next.handle(request);
  }
}
