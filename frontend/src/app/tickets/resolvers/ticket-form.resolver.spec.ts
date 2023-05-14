import { TestBed } from '@angular/core/testing';

import { TicketFormResolver } from './ticket-form.resolver';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('TicketFormResolver', () => {
  let resolver: TicketFormResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    resolver = TestBed.inject(TicketFormResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
