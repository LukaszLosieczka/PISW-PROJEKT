import { TestBed } from '@angular/core/testing';

import { DiscountsResolver } from './discounts.resolver';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('DiscountsResolver', () => {
  let resolver: DiscountsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    resolver = TestBed.inject(DiscountsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
