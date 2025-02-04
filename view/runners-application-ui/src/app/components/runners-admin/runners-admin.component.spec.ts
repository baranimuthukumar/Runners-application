import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunnersAdminComponent } from './runners-admin.component';

describe('RunnersAdminComponent', () => {
  let component: RunnersAdminComponent;
  let fixture: ComponentFixture<RunnersAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RunnersAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RunnersAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
