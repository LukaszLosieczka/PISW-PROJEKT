import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Discount} from "../model/discount";

const discountsApiPrefix = 'http://localhost:8080/discounts/available_discounts';

@Injectable({
  providedIn: 'root'
})
export class DiscountService {

  constructor(private readonly http: HttpClient) {}

  getAllDiscounts(): Observable<Discount[]>{
    return this.http.get<Discount[]>(discountsApiPrefix);
  }
}
