import { TestBed } from '@angular/core/testing';

import { HttpExaminationPractitionerService } from './http-examination-practitioner.service';

describe('HttpExaminationPractitionerService', () => {
  let service: HttpExaminationPractitionerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpExaminationPractitionerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
