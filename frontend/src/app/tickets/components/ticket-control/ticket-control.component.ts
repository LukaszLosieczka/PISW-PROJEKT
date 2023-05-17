import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserTicket } from '../../model/user-ticket';
import { UserService } from '../../../shared/services/user.service';

@Component({
  selector: 'bs-ticket-validation',
  templateUrl: './ticket-control.component.html',
  styleUrls: ['./ticket-control.component.scss'],
})
export class TicketControlComponent implements OnInit {
  form: FormGroup;
  ticket: UserTicket;
  ticketCode: number;
  vehicleId: number;
  ticketValidationMessage: string;
  submitted = false;
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      code: new FormControl(this.ticketCode, [
        Validators.required,
        Validators.min(100000),
        Validators.max(999999)
      ]),
      vehicle: new FormControl(this.vehicleId, [Validators.required])
    });

    this.form.get('code')?.valueChanges.subscribe((v) => {
      if (v) {
        this.submitted = false;
        this.onCodeChange(v);
      }
    });

    this.form.get('vehicle')?.valueChanges.subscribe((v) => {
      if (v) {
        this.submitted = false;
        this.onVehicleChange(v);
      }
    });
  }

  get f() {
    return this.form.controls;
  }

  onControlTicket(): void {
    this.submitted = true;

    const now = new Date();

    if (this.ticketValidationMessage === 'Podano błędny kod biletu' ||
      this.ticketValidationMessage === 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze') {
      return
    }

    if (this.ticket.validation !== null) {
      const dateString = this.ticket.validation?.validation_time;
      const dateParts = dateString.split(", ");
      const date = dateParts[0].split(".").reverse().join("-");
      const time = dateParts[1];
      const formattedDateString = `${date}T${time}`;
      const validationTime = new Date(formattedDateString);
      const validityPeriodInMillis = this.ticket.ticket.validityPeriod * 1000;

      console.log(validationTime.getTime())
      console.log(validationTime.getTime() + validityPeriodInMillis)
      console.log(now.getTime())
      if (this.ticket.ticket.type === 'time' && validationTime.getTime() + validityPeriodInMillis >= now.getTime()) {
        this.ticketValidationMessage = 'Bilet jest ważny';
      } else {
        this.ticketValidationMessage = 'Bilet jest nieważny';
      }
      if (this.ticket.ticket.type === 'single') {
        this.ticketValidationMessage = 'Bilet jest ważny';
      }
    } else {
      this.ticketValidationMessage = 'Bilet nie został skasowany';
    }
  }

  private onCodeChange(code: number): void {
    const ticketExists = this.userService.checkTicketExists(code);
    this.ticketCode = code;

    if (ticketExists) {
      this.ticket = this.userService.userTickets.filter(ticket => ticket.code === Number(this.ticketCode))[0];
      this.ticketValidationMessage = '';
    } else {
      this.ticketValidationMessage = 'Podano błędny kod biletu';
    }
  }

  private onVehicleChange(vehicle: number): void {
    const hasVehicle = this.userService.checkTicketHasVehicle(this.ticketCode, vehicle);

    if (hasVehicle) {
      this.ticketValidationMessage = '';
    } else {
      this.ticketValidationMessage = 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze';
    }
  }

}
