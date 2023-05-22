import {ComponentFixture, TestBed} from '@angular/core/testing';
import {TicketsListTableComponent} from './tickets-list-table.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {TicketsModule} from "../../../tickets.module";
import {AppRoutingModule} from "../../../../app-routing.module";
import {RouterModule} from "@angular/router";

describe('TicketsListTableComponent', () => {
  let component: TicketsListTableComponent;
  let fixture: ComponentFixture<TicketsListTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        TicketsModule,
        AppRoutingModule,
        HttpClientModule,
        RouterModule],
      declarations: [ TicketsListTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketsListTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
