jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PaynowService } from '../service/paynow.service';
import { IPaynow, Paynow } from '../paynow.model';

import { PaynowUpdateComponent } from './paynow-update.component';

describe('Paynow Management Update Component', () => {
  let comp: PaynowUpdateComponent;
  let fixture: ComponentFixture<PaynowUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paynowService: PaynowService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PaynowUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PaynowUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaynowUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paynowService = TestBed.inject(PaynowService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paynow: IPaynow = { id: 456 };

      activatedRoute.data = of({ paynow });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(paynow));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paynow>>();
      const paynow = { id: 123 };
      jest.spyOn(paynowService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paynow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paynow }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(paynowService.update).toHaveBeenCalledWith(paynow);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paynow>>();
      const paynow = new Paynow();
      jest.spyOn(paynowService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paynow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paynow }));
      saveSubject.complete();

      // THEN
      expect(paynowService.create).toHaveBeenCalledWith(paynow);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Paynow>>();
      const paynow = { id: 123 };
      jest.spyOn(paynowService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paynow });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paynowService.update).toHaveBeenCalledWith(paynow);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
