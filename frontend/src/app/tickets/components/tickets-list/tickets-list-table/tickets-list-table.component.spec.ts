import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketsListTableComponent } from './tickets-list-table.component';

describe('TicketsListTableComponent', () => {
  let component: TicketsListTableComponent;
  let fixture: ComponentFixture<TicketsListTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
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
