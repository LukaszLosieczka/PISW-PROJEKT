import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Ticket} from "../../model/ticket";

@Component({
  selector: 'bs-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrls: ['./tickets-list.component.scss']
})
export class TicketsListComponent implements OnInit {

  readonly tickets: Ticket[]

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.tickets = activatedRoute.snapshot.data['tickets'];
  }

  ngOnInit(): void {
  }

}
