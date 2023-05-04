import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookListComponent } from './book-list.component';
import {ActivatedRoute} from "@angular/router";

describe('BookListComponent', () => {
  let component: BookListComponent;
  let fixture: ComponentFixture<BookListComponent>;
  let activatedRouteMock: any;

  beforeEach(() => {
    activatedRouteMock = {
      snapshot: {
        data: {
          books: []
        }
      }
    };
  });

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookListComponent ],
      providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
