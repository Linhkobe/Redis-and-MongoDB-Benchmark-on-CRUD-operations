import { TestBed } from '@angular/core/testing';

import { RatingRedisService } from './rating-redis.service';

describe('RatingRedisService', () => {
  let service: RatingRedisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RatingRedisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
