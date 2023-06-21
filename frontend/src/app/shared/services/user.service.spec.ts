import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../../tickets/tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('UserService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
