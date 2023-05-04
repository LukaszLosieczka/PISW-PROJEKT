import { Component } from '@angular/core';
import { Book } from '../../model/book';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'bs-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent {

  readonly books: Book[];

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.books = this.activatedRoute.snapshot.data['books'];
  }
}
