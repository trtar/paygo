import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaynowDetailComponent } from './paynow-detail.component';

describe('Paynow Management Detail Component', () => {
  let comp: PaynowDetailComponent;
  let fixture: ComponentFixture<PaynowDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaynowDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paynow: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaynowDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaynowDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paynow on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paynow).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
