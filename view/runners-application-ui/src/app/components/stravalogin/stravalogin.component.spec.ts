import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StravaloginComponent } from './stravalogin.component';

describe('StravaloginComponent', () => {
  let component: StravaloginComponent;
  let fixture: ComponentFixture<StravaloginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StravaloginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StravaloginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
