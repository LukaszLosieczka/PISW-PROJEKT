import { Injectable } from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';
import { BooksService } from '../services/books.service';
import { Book } from '../model/book';

@Injectable({
  providedIn: 'root'
})
export class BookListResolver implements Resolve<Book[]> {

  constructor(private readonly booksService: BooksService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Book[]> {
    return this.booksService.getAllBooks();
  }
}
