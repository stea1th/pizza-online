import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";

@Component({
  selector: 'app-person-tabs',
  templateUrl: './person-tabs.component.html',
  styleUrls: ['./person-tabs.component.css']
})
export class PersonTabsComponent implements OnInit {

  personDetails: PersonDetails;

  constructor(private data: DataService) {
  }

  ngOnInit(): void {
    this.data.getPersonDetails().subscribe(data => {
      this.personDetails = data;
    });
  }
}

export interface PersonDetails {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  address: AddressDetails;

}

export interface AddressDetails {
  id: number;
  street: string;
  zip: string;
  city: string;
  country: string;
}
