import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Discount} from "../model/discount";
import {environment} from "../../../environments/environment";

const discountsApiPrefix = `${environment.apiUrl}discounts/available_discounts`;

@Injectable({
  providedIn: 'root'
})
export class DiscountService {

  constructor(private readonly http: HttpClient) {}

  getAllDiscounts(): Observable<Discount[]>{
    return this.http.get<Discount[]>(discountsApiPrefix);
  }
}
