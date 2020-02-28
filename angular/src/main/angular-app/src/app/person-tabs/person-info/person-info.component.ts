import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {PersonDetails} from "../person-tabs.component";
import {DataService} from "../../service/data.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-person-info',
  templateUrl: './person-info.component.html',
  styleUrls: ['./person-info.component.css']
})
export class PersonInfoComponent implements OnInit {
  personDetailsForm: any;

  @Input() personDetails: PersonDetails;

  constructor(private _data: DataService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.personDetailsForm = new FormGroup({
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      street: new FormControl(''),
      city: new FormControl(''),
      zip: new FormControl(''),
      country: new FormControl('')
    });
  }

  onSubmit() {
    if(!this.personDetailsForm.pristine) {
      this.personDetails.firstName = this.personDetailsForm.value.firstName;
      this.personDetails.lastName = this.personDetailsForm.value.lastName;
      this.personDetails.address.street = this.personDetailsForm.value.street;
      this.personDetails.address.zip = this.personDetailsForm.value.zip;
      this.personDetails.address.city = this.personDetailsForm.value.city;
      this.personDetails.address.country = this.personDetailsForm.value.country;
      this._data.savePersonDetails(this.personDetails).subscribe(d=> {
        this._snackBar.open("Your detail changes are saved successfully");
        this.personDetailsForm.pristine = true;
      });
    }

  }

}
