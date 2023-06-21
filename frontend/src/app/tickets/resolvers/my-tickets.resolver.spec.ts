import { TestBed } from '@angular/core/testing';

import { MyTicketsResolver } from './my-tickets.resolver';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('MyTicketsResolver', () => {
  let resolver: MyTicketsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    resolver = TestBed.inject(MyTicketsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
