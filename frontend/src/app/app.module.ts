import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TicketsModule} from "./tickets/tickets.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import { NavbarComponent } from './core/navbar/navbar.component';
import { HomeLayoutComponent } from './layouts/home-layout/home-layout.component';
import { LoginLayoutComponent } from './layouts/login-layout/login-layout.component';
import {CommonModule} from "@angular/common";
import {UserModule} from "./user/user.module";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeLayoutComponent,
    LoginLayoutComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    TicketsModule,
    UserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
