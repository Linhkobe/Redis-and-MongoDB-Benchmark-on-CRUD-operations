import { TestBed } from '@angular/core/testing';

import { CrudRedisService } from './crud-redis.service';

describe('CrudRedisService', () => {
  let service: CrudRedisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrudRedisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
