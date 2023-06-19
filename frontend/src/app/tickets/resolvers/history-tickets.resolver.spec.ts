import { TestBed } from '@angular/core/testing';

import { HistoryTicketsResolver } from './history-tickets.resolver';

describe('HistoryTicketsResolver', () => {
  let resolver: HistoryTicketsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(HistoryTicketsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
