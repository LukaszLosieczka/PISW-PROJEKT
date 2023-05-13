import { TestBed } from '@angular/core/testing';

import { TicketsListResolver } from './tickets-list.resolver';

describe('TicketsListResolver', () => {
  let resolver: TicketsListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(TicketsListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
