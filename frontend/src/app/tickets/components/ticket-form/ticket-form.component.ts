import {Component, OnInit} from '@angular/core';
import {Ticket} from "../../model/ticket";
import {Discount} from "../../model/discount";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserTicketService} from "../../services/user-ticket.service";

@Component({
  selector: 'bs-ticket-form',
  templateUrl: './ticket-form.component.html',
  styleUrls: ['./ticket-form.component.scss']
})
export class TicketFormComponent implements OnInit {

  form: FormGroup;

  readonly ticket: Ticket;
  readonly discounts: Discount[];

  ticketsQuantity: number = 1;
  chosenDiscount: Discount | null;
  fullPrice: number;

  constructor(private activatedRoute: ActivatedRoute, private userTicketService: UserTicketService) {
    this.ticket = this.activatedRoute.snapshot.data['ticket'];
    this.discounts = this.activatedRoute.snapshot.data['discounts'];
  }

  calculatePrice(): number {
    let price = this.ticket.price;

    if (this.chosenDiscount !== null) {
      price -= (this.chosenDiscount.discountPercent / 100 * this.ticket.price)
    }
    return price * this.ticketsQuantity;
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      quantity: new FormControl(this.ticketsQuantity, [Validators.min(1)]),
      discount: new FormControl(this.chosenDiscount),
      price: new FormControl(this.fullPrice)
    })
    this.ticketsQuantity = 1;
    this.chosenDiscount = null;
    this.fullPrice = this.calculatePrice();

    this.form.get("discount")?.valueChanges.subscribe(f => {
      this.onDiscountChange(f);
    });

    this.form.get("quantity")?.valueChanges.subscribe(f => {
      this.onQuantityChange(f);
    });
  }

  onDiscountChange(discount: Discount): void {
    this.chosenDiscount = discount;
    this.fullPrice = Math.round(this.calculatePrice() * 100) / 100;
  }

  onQuantityChange(quantity: number): void {
    this.ticketsQuantity = quantity;
    this.fullPrice = Math.round(this.calculatePrice() * 100) / 100;
  }

  onBuyNow(): void {
    this.userTicketService.Ticket = this.ticket;
    this.userTicketService.Discount = this.chosenDiscount;
    this.userTicketService.Quantity = this.ticketsQuantity;

    this.userTicketService.addNewUserTickets();
  }

}
