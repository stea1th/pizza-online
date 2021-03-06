import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {faEuroSign} from "@fortawesome/free-solid-svg-icons";
import { Location } from '@angular/common';


@Component({
  selector: 'app-footer-total',
  templateUrl: './footer-total.component.html',
  styleUrls: ['./footer-total.component.css']
})
export class FooterTotalComponent implements OnInit {

  @Input() totalQuantity: number;
  @Input() totalPay: string;

  @Input() createTransactionItems: EventEmitter<any>;

  faEuro = faEuroSign;

  constructor(private _location: Location) { }

  ngOnInit(): void {

  }

  goBack() {
    this._location.back();
  }

  onClick() {
    this.createTransactionItems.emit();
  }



}
