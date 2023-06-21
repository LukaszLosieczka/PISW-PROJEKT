import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
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
import {TicketControlComponent} from "./tickets/components/ticket-control/ticket-control.component";
import {AuthGuard} from "./utils/auth.guard";
import {MyTicketsResolver} from "./tickets/resolvers/my-tickets.resolver";
import {HistoryTicketsResolver} from "./tickets/resolvers/history-tickets.resolver";

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
        canActivate: [AuthGuard],
        data: {
          roles: ['ROLE_USER']
        },
        component: TicketFormComponent,
        resolve: {
          ticket: TicketFormResolver,
          discounts: DiscountsResolver
        }
      },
      {
        path: 'my-tickets',
        canActivate: [AuthGuard],
        data: {
          roles: ['ROLE_USER']
        },
        component: MyTicketsComponent,
        resolve: {
          myTickets: MyTicketsResolver,
          historyTickets: HistoryTicketsResolver
        }
      },
      {
        path: 'ticket-control',
        canActivate: [AuthGuard],
        data: {
          roles: ['ROLE_TICKET_COLLECTOR']
        },
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
        canActivate: [AuthGuard],
        component: LoginComponent
      },
      {
        path: 'register',
        canActivate: [AuthGuard],
        component: RegisterComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
