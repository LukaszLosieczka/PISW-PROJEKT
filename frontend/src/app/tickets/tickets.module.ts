import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketsListComponent } from './components/tickets-list/tickets-list.component';
import { TicketsListTableComponent } from './components/tickets-list/tickets-list-table/tickets-list-table.component';



@NgModule({
  declarations: [
    TicketsListComponent,
    TicketsListTableComponent
  ],
  imports: [
    CommonModule
  ]
})
export class TicketsModule { }
