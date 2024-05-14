import { TestBed } from '@angular/core/testing';

import { UserRedisService } from './user-redis.service';

describe('UserRedisService', () => {
  let service: UserRedisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRedisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

