import { TestBed } from '@angular/core/testing';

import { BookListResolver } from './book-list.resolver';
import {BooksService} from "../services/books.service";

describe('BookListResolver', () => {
  let resolver: BookListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: BooksService, useValue: {} }
      ]
    });
    resolver = TestBed.inject(BookListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
