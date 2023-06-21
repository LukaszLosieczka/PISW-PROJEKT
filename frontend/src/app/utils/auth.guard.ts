import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {UserService} from "../shared/services/user.service";

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private userService: UserService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const path = this.getResolvedUrl(route);
    if (this.userService.isLoggedIn) {
      const userRole = this.userService.getUserRole();
      if(path === 'login' || path === 'register'){
        this.router.navigate(['/'])
        return false;
      }
      else if(route.data.roles.includes(userRole)){
        return true;
      }
      this.router.navigate(['/']);
      return false;
    }

    if(path === 'login' || path === 'register'){
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

  private getResolvedUrl(route: ActivatedRouteSnapshot): string {
    return route.pathFromRoot
      .map(v => v.url.map(segment => segment.toString()).join('/'))
      .join('');
  }
}
