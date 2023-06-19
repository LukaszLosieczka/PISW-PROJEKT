import { TestBed } from '@angular/core/testing';

import { MyTicketsResolver } from './my-tickets.resolver';

describe('MyTicketsResolver', () => {
  let resolver: MyTicketsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(MyTicketsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
