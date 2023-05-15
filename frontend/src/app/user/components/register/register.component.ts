import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../shared/services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'bs-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;

  firstName = "";
  lastName = "";
  username = "";
  password = "";

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      firstName: new FormControl(this.username, [Validators.required]),
      lastName: new FormControl(this.lastName, [Validators.required]),
      username: new FormControl(this.username, [Validators.required]),
      password: new FormControl(this.password, [Validators.required, Validators.minLength(6)])
    });
  }

  get f() { return this.form.controls; }

  onSubmit(): void{
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.userService.register();

    this.router.navigate(['/login']);
  }

}