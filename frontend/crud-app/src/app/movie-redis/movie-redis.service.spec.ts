import { TestBed } from '@angular/core/testing';

import { MovieRedisService } from './movie-redis.service';

describe('MovieRedisService', () => {
  let service: MovieRedisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieRedisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
