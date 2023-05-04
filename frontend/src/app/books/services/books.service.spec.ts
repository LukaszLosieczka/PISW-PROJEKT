import { TestBed } from '@angular/core/testing';

import { BooksService } from './books.service';
import {HttpClient} from "@angular/common/http";

describe('BooksService', () => {
  let service: BooksService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpClient, useValue: {} }
      ]
    });
    service = TestBed.inject(BooksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
