import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserTicket} from '../../model/user-ticket';
import {UserTicketService} from "../../services/user-ticket.service";

@Component({
  selector: 'bs-ticket-validation',
  templateUrl: './ticket-control.component.html',
  styleUrls: ['./ticket-control.component.scss'],
})
export class TicketControlComponent implements OnInit {
  form: FormGroup;
  userTicket: UserTicket;
  userTicketCode: number;
  userTicketVehicle: number;
  userTicketValidationMessage: string;
  isSubmitted = false;

  constructor(private userTicketService: UserTicketService) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      code: new FormControl(this.userTicketCode, [
        Validators.required,
        Validators.min(100000),
        Validators.max(999999)
      ]),
      vehicle: new FormControl(this.userTicketVehicle, [
        Validators.required,
        Validators.min(1000),
        Validators.max(9999)
      ])
    });

    this.form.get('code')?.valueChanges.subscribe(v => {
      if (v) {
        this.isSubmitted = false;
        this.onUserTicketCodeChange(v);
      }
    });

    this.form.get('vehicle')?.valueChanges.subscribe(v => {
      if (v) {
        this.isSubmitted = false;
        this.onUserTicketVehicleChange(v);
      }
    });
  }

  get getFormControls() {
    return this.form.controls;
  }

  convertUserTicketValidationTimeFormat(): Date {
    const userTicketValidationTimeString = this.userTicket.validation?.validation_time;
    // @ts-ignore
    const userTicketValidationTimeStringParts = userTicketValidationTimeString.split(", ");
    const date = userTicketValidationTimeStringParts[0].split(".").reverse().join("-");
    const time = userTicketValidationTimeStringParts[1];
    const formattedUserTicketValidationTimeString = `${date}T${time}`;
    const userTicketValidationTime = new Date(formattedUserTicketValidationTimeString);

    return userTicketValidationTime
  }

  onUserTicketControl(): void {
    const invalidUserTicketVehicle = 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze';
    const invalidUserTicketCode = 'Podano błędny kod biletu';
    const now = new Date();

    this.isSubmitted = true;

    if (this.userTicketValidationMessage === invalidUserTicketCode ||
        this.userTicketValidationMessage === invalidUserTicketVehicle) {
      return
    }

    if (this.userTicket.validation !== null) {
      if (this.userTicket.ticket.ticketType === 'SINGLE') {
        this.userTicketValidationMessage = 'Bilet jest ważny';
        return;
      }

      const userTicketValidationTime = this.convertUserTicketValidationTimeFormat();
      const userTicketValidityPeriodInMillis = this.userTicket.ticket.validityPeriodSec * 1000;

      if (this.userTicket.ticket.ticketType === 'TIME' && userTicketValidationTime.getTime() + userTicketValidityPeriodInMillis >= now.getTime()) {
        this.userTicketValidationMessage = 'Bilet jest ważny';
      } else {
        this.userTicketValidationMessage = 'Bilet jest nieważny';
      }
    } else {
      this.userTicketValidationMessage = 'Bilet nie został skasowany';
    }
  }

  private onUserTicketCodeChange(userTicketCode: number): void {
    const userTicketExists = this.userTicketService.checkUserTicketExists(userTicketCode);
    this.userTicketCode = userTicketCode;

    if (userTicketExists) {
      this.userTicket = this.userTicketService.userTickets.filter(ticket => ticket.code === Number(this.userTicketCode))[0];
      this.userTicketValidationMessage = '';
    } else {
      this.userTicketValidationMessage = 'Podano błędny kod biletu';
    }
  }

  private onUserTicketVehicleChange(userTicketVehicle: number): void {
    const hasVehicle = this.userTicketService.checkUserTicketHasVehicle(this.userTicketCode, userTicketVehicle);

    if (hasVehicle) {
      this.userTicketValidationMessage = '';
    } else {
      this.userTicketValidationMessage = 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze';
    }
  }

}
