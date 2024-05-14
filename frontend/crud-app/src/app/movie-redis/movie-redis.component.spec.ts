import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieRedisComponent } from './movie-redis.component';

describe('MovieRedisComponent', () => {
  let component: MovieRedisComponent;
  let fixture: ComponentFixture<MovieRedisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MovieRedisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MovieRedisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
