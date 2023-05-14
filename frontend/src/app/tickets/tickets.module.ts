import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketsListComponent } from './components/tickets-list/tickets-list.component';
import { TicketsListTableComponent } from './components/tickets-list/tickets-list-table/tickets-list-table.component';
import { TicketFormComponent } from './components/ticket-form/ticket-form.component';
import {RouterModule} from "@angular/router";
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    TicketsListComponent,
    TicketsListTableComponent,
    TicketFormComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class TicketsModule { }
