import { TestBed } from '@angular/core/testing';

import { TicketsListResolver } from './tickets-list.resolver';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('TicketsListResolver', () => {
  let resolver: TicketsListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    resolver = TestBed.inject(TicketsListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
