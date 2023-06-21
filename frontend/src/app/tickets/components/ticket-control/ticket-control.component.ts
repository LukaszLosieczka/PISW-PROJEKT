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
  userTicketCode: string;
  userTicketVehicle: string;
  userTicketValidationMessage: string;
  isSubmitted = false;
  isValid: boolean;

  constructor(private userTicketService: UserTicketService) {}

  ngOnInit(): void {
    this.form = new FormGroup({
      code: new FormControl(this.userTicketCode, [
        Validators.required,
        Validators.minLength(36),
        Validators.maxLength(36)
      ]),
      vehicle: new FormControl(this.userTicketVehicle, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(8)
      ])
    });

    this.form.get('code')?.valueChanges.subscribe(v => {
      if (v) {
        this.isSubmitted = false;
        this.userTicketCode = v;
      }
    });

    this.form.get('vehicle')?.valueChanges.subscribe(v => {
      if (v) {
        this.isSubmitted = false;
        this.userTicketVehicle = v;
      }
    });
  }

  get getFormControls() {
    return this.form.controls;
  }

  onUserTicketControl(): void{
    this.isSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    this.userTicketService.isTicketValid(this.userTicketCode, this.userTicketVehicle)
      .subscribe({
        next: data => {
          this.userTicketValidationMessage = data.message;
          this.isValid = data.isValid;
        },
        error: err => {
          this.isValid = false;
          this.userTicketValidationMessage = err.error;
        }
      });
  }

  // convertUserTicketValidationTimeFormat(): Date {
  //   const userTicketValidationTimeString = this.userTicket.validation?.validation_time;
  //   // @ts-ignore
  //   const userTicketValidationTimeStringParts = userTicketValidationTimeString.split(", ");
  //   const date = userTicketValidationTimeStringParts[0].split(".").reverse().join("-");
  //   const time = userTicketValidationTimeStringParts[1];
  //   const formattedUserTicketValidationTimeString = `${date}T${time}`;
  //   const userTicketValidationTime = new Date(formattedUserTicketValidationTimeString);
  //
  //   return userTicketValidationTime
  // }

  // onUserTicketControl(): void {
  //   const invalidUserTicketVehicle = 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze';
  //   const invalidUserTicketCode = 'Podano błędny kod biletu';
  //   const now = new Date();
  //
  //   this.isSubmitted = true;
  //
  //   if (this.userTicketValidationMessage === invalidUserTicketCode ||
  //       this.userTicketValidationMessage === invalidUserTicketVehicle) {
  //     return
  //   }
  //
  //   if (this.userTicket.validation !== null) {
  //     if (this.userTicket.ticket.ticketType === 'SINGLE') {
  //       this.userTicketValidationMessage = 'Bilet jest ważny';
  //       return;
  //     }
  //
  //     const userTicketValidationTime = this.convertUserTicketValidationTimeFormat();
  //     const userTicketValidityPeriodInMillis = this.userTicket.ticket.validityPeriodSec * 1000;
  //
  //     if (this.userTicket.ticket.ticketType === 'TIME' && userTicketValidationTime.getTime() + userTicketValidityPeriodInMillis >= now.getTime()) {
  //       this.userTicketValidationMessage = 'Bilet jest ważny';
  //     } else {
  //       this.userTicketValidationMessage = 'Bilet jest nieważny';
  //     }
  //   } else {
  //     this.userTicketValidationMessage = 'Bilet nie został skasowany';
  //   }
  // }
  //
  // private onUserTicketCodeChange(userTicketCode: number): void {
  //   const userTicketExists = this.userTicketService.checkUserTicketExists(userTicketCode);
  //   this.userTicketCode = userTicketCode;
  //
  //   if (userTicketExists) {
  //     this.userTicket = this.userTicketService.userTickets.filter(ticket => ticket.code === Number(this.userTicketCode))[0];
  //     this.userTicketValidationMessage = '';
  //   } else {
  //     this.userTicketValidationMessage = 'Podano błędny kod biletu';
  //   }
  // }
  //
  // private onUserTicketVehicleChange(userTicketVehicle: number): void {
  //   const hasVehicle = this.userTicketService.checkUserTicketHasVehicle(this.userTicketCode, userTicketVehicle);
  //
  //   if (hasVehicle) {
  //     this.userTicketValidationMessage = '';
  //   } else {
  //     this.userTicketValidationMessage = 'Bilet nie został skasowany w pojedzie o podanym identyfiaktorze';
  //   }
  // }

}
