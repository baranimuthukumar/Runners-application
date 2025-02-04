import { TestBed } from '@angular/core/testing';

import { RunnerProfileService } from './runner-profile.service';

describe('RunnerProfileServiceService', () => {
  let service: RunnerProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RunnerProfileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
