import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunnersProfileComponent } from './runners-profile.component';

describe('RunnersProfileComponent', () => {
  let component: RunnersProfileComponent;
  let fixture: ComponentFixture<RunnersProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RunnersProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RunnersProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
