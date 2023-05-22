import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TicketsListComponent} from './components/tickets-list/tickets-list.component';
import {TicketsListTableComponent} from './components/tickets-list/tickets-list-table/tickets-list-table.component';
import {TicketFormComponent} from './components/ticket-form/ticket-form.component';
import {RouterModule} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";
import {MyTicketsComponent} from "./components/my-tickets/my-tickets.component";
import {MyTicketsTableComponent} from "./components/my-tickets/my-tickets-table/my-tickets-table.component";
import {MyTicketsHistoryComponent} from "./components/my-tickets/my-tickets-history/my-tickets-history.component";
import {TicketControlComponent} from "./components/ticket-control/ticket-control.component";

@NgModule({
  declarations: [
    TicketsListComponent,
    TicketsListTableComponent,
    TicketFormComponent,
    MyTicketsComponent,
    MyTicketsTableComponent,
    MyTicketsHistoryComponent,
    TicketControlComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ]
})

export class TicketsModule {}
