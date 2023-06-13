import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../shared/services/user.service";
import {Router} from "@angular/router";
import {first} from "rxjs";

@Component({
  selector: 'bs-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.form = new FormGroup({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
    });
  }

  get f() { return this.form.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.userService.logIn(this.form.get("username")?.value, this.form.get("password")?.value)
      .pipe(first())
      .subscribe({
        next: () => {
          this.router.navigate(['/']);
        },
        error: error => {
          console.log(error)
          this.loading = false;
        }
      })
  }

}
