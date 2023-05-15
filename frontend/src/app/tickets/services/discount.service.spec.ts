import { TestBed } from '@angular/core/testing';

import { DiscountService } from './discount.service';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../tickets.module";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('DiscountService', () => {
  let service: DiscountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule]
    });
    service = TestBed.inject(DiscountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
