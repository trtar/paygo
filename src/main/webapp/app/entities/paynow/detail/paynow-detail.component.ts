import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaynow } from '../paynow.model';

@Component({
  selector: 'jhi-paynow-detail',
  templateUrl: './paynow-detail.component.html',
})
export class PaynowDetailComponent implements OnInit {
  paynow: IPaynow | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paynow }) => {
      this.paynow = paynow;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
