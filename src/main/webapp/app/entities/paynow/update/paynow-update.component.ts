import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPaynow, Paynow } from '../paynow.model';
import { PaynowService } from '../service/paynow.service';

@Component({
  selector: 'jhi-paynow-update',
  templateUrl: './paynow-update.component.html',
})
export class PaynowUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cik: [],
    ccc: [],
    name: [],
    email: [],
    phone: [],
  });

  constructor(protected paynowService: PaynowService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paynow }) => {
      this.updateForm(paynow);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paynow = this.createFromForm();
    if (paynow.id !== undefined) {
      this.subscribeToSaveResponse(this.paynowService.update(paynow));
    } else {
      this.subscribeToSaveResponse(this.paynowService.create(paynow));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaynow>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paynow: IPaynow): void {
    this.editForm.patchValue({
      id: paynow.id,
      cik: paynow.cik,
      ccc: paynow.ccc,
      name: paynow.name,
      email: paynow.email,
      phone: paynow.phone,
    });
  }

  protected createFromForm(): IPaynow {
    return {
      ...new Paynow(),
      id: this.editForm.get(['id'])!.value,
      cik: this.editForm.get(['cik'])!.value,
      ccc: this.editForm.get(['ccc'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }
}
