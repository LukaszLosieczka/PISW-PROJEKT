import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsListComponent } from './tickets-list.component';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../../tickets.module";
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('TicketsListComponent', () => {
  let component: TicketsListComponent;
  let fixture: ComponentFixture<TicketsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule],
      declarations: [ TicketsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
