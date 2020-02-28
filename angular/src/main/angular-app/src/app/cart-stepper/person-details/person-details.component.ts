import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PersonDetails} from "../../person-tabs/person-tabs.component";
import {DataService} from "../../service/data.service";

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.css']
})
export class PersonDetailsComponent implements OnInit {
  firstFormGroup: any;
  secondFormGroup: any;
  isLinear = true;
  isDisabled = true;
  personDetails: PersonDetails;
  needToSave = false;
  orderDateTime: OrderDateTime;

  @Output() setOrderDateTime = new EventEmitter();

  constructor(private _data: DataService) {
  }

  ngOnInit(): void {
    this._data.getPersonDetails().subscribe(data => {
      this.personDetails = data;
    });
    this.firstFormGroup = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required)
    });
    this.secondFormGroup = new FormGroup({
      street: new FormControl('', Validators.required),
      city: new FormControl('', Validators.required),
      zip: new FormControl('', Validators.required),
      country: new FormControl('', Validators.required)
    });
  }

  saveDetails() {
    if (this.firstFormGroup.touched) {
      this.personDetails.firstName = this.firstFormGroup.value.firstName;
      this.personDetails.lastName = this.firstFormGroup.value.lastName;
      this.needToSave = true;
    }
    if (this.secondFormGroup.touched) {
      this.personDetails.address.street = this.secondFormGroup.value.street;
      this.personDetails.address.zip = this.secondFormGroup.value.zip;
      this.personDetails.address.city = this.secondFormGroup.value.city;
      this.personDetails.address.country = this.secondFormGroup.value.country;
      this.needToSave = true;
    }
    if (this.needToSave) {
      this._data.savePersonDetails(this.personDetails).subscribe(d => console.log(d));
    }
  }

  completeOrder() {
    this._data.getCompleteOrderTime().subscribe(d => {
      this.orderDateTime = d;
      this.setOrderDateTime.emit(this.orderDateTime);
    });
  }
}

export interface OrderDateTime {
  year: number;
  monthValue: number;
  dayOfMonth: number;
  hour: number;
  minute: number;
}
