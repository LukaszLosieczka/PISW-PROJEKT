import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TicketsListComponent} from "./tickets/components/tickets-list/tickets-list.component";
import {TicketsListResolver} from "./tickets/resolvers/tickets-list.resolver";

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
