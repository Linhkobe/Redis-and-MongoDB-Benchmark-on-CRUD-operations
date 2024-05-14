import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingRedisComponent } from './rating-redis.component';

describe('RatingRedisComponent', () => {
  let component: RatingRedisComponent;
  let fixture: ComponentFixture<RatingRedisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RatingRedisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RatingRedisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
