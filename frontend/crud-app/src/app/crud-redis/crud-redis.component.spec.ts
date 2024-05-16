import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudRedisComponent } from './crud-redis.component';

describe('CrudRedisComponent', () => {
  let component: CrudRedisComponent;
  let fixture: ComponentFixture<CrudRedisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CrudRedisComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CrudRedisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
