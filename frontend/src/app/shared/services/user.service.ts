import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  isLoggedIn: boolean = false;

  constructor() { }

  logIn(username:string, password: string): void{
    this.isLoggedIn = true;
  }

  logout(): void {
    this.isLoggedIn = false;
  }

  register(): void{

  }

}
