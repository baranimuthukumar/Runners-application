import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarathonDetailComponent } from './marathon-detail.component';

describe('MarathonDetailComponent', () => {
  let component: MarathonDetailComponent;
  let fixture: ComponentFixture<MarathonDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarathonDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarathonDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
