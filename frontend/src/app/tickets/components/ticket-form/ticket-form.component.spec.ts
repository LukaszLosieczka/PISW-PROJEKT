import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketFormComponent } from './ticket-form.component';
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../../tickets.module";
import {AppRoutingModule} from "../../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";

describe('TicketFormComponent', () => {
  let component: TicketFormComponent;
  let fixture: ComponentFixture<TicketFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule],
      declarations: [ TicketFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
