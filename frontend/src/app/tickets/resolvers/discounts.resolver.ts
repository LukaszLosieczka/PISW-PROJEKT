import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {DiscountService} from "../services/discount.service";
import {Discount} from "../model/discount";

@Injectable({
  providedIn: 'root'
})
export class DiscountsResolver implements Resolve<Discount[]> {

  constructor(private readonly discountService: DiscountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Discount[]> {
    return this.discountService.getAllDiscounts();
  }
}
