import {Component, OnInit} from '@angular/core';
import {UserService} from "../../shared/services/user.service";

@Component({
  selector: 'bs-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private userService: UserService) {}

  ngOnInit(): void {}

  isLoggedIn(): boolean {
    return this.userService.isLoggedIn;
  }

  logout(): void {
    this.userService.logout();
  }

}
