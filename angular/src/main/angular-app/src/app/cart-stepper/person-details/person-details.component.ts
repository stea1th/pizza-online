import { Component, OnInit } from '@angular/core';
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

  constructor(private data: DataService) { }

  ngOnInit(): void {
    this.data.getPersonDetails().subscribe(data => {
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

  toggleDisabled() {
    this.isDisabled = !this.isDisabled;
  }

}
