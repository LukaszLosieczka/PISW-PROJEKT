import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TicketsListComponent} from "./tickets/components/tickets-list/tickets-list.component";
import {TicketsListResolver} from "./tickets/resolvers/tickets-list.resolver";
import {TicketFormComponent} from "./tickets/components/ticket-form/ticket-form.component";
import {TicketFormResolver} from "./tickets/resolvers/ticket-form.resolver";
import {DiscountsResolver} from "./tickets/resolvers/discounts.resolver";
import {HomeLayoutComponent} from "./layouts/home-layout/home-layout.component";
import {LoginLayoutComponent} from "./layouts/login-layout/login-layout.component";
import {LoginComponent} from "./user/components/login/login.component";
import {RegisterComponent} from "./user/components/register/register.component";
import {MyTicketsComponent} from "./tickets/components/my-tickets/my-tickets.component";
import {MyTicketsResolver} from "./tickets/resolvers/my-tickets.resolver";
import {TicketControlComponent} from "./tickets/components/ticket-control/ticket-control.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/tickets'
  },
  {
    path: '',
    component: HomeLayoutComponent,
    children: [

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
      },
      {
        path: 'my-tickets',
        component: MyTicketsComponent
      },
      {
        path: 'ticket-control',
        component: TicketControlComponent
      }
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [

      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
