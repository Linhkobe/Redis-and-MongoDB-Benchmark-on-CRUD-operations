import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRedisComponent } from './user-redis.component';

describe('UserRedisComponent', () => {
  let component: UserRedisComponent;
  let fixture: ComponentFixture<UserRedisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserRedisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserRedisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
