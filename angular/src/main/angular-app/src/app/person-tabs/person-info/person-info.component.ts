import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {PersonDetails} from "../person-tabs.component";

@Component({
  selector: 'app-person-info',
  templateUrl: './person-info.component.html',
  styleUrls: ['./person-info.component.css']
})
export class PersonInfoComponent implements OnInit {
  personDetailsForm: any;

  @Input() personDetails: PersonDetails;

  constructor() { }

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

}
