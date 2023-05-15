import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TicketsListComponent} from "./tickets/components/tickets-list/tickets-list.component";
import {TicketsListResolver} from "./tickets/resolvers/tickets-list.resolver";
import {TicketFormComponent} from "./tickets/components/ticket-form/ticket-form.component";
import {TicketFormResolver} from "./tickets/resolvers/ticket-form.resolver";
import {DiscountsResolver} from "./tickets/resolvers/discounts.resolver";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/tickets'
  },
  {
    path: 'tickets',
    component: TicketsListComponent,
    resolve: {
      tickets: TicketsListResolver
    }
  },
  {
    path: 'tickets/:id',
    component: TicketFormComponent,
    resolve: {
      ticket: TicketFormResolver,
      discounts: DiscountsResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
