import { Injectable } from '@angular/core';
import {ProductElement} from "../model/product-element.model";
import {MatTableDataSource} from "@angular/material/table";
import {DomSanitizer} from "@angular/platform-browser";
import {PriceService} from "./price.service";

@Injectable({
  providedIn: 'root'
})
export class TableDataSourceService {

  constructor(private _sanitizer: DomSanitizer,
              private _price: PriceService) { }

  public fillTableWithData(data :ProductElement[]) : MatTableDataSource<ProductElement> {
    const myDataSource = new MatTableDataSource<ProductElement>();
    myDataSource.data = data as ProductElement[];
    for (let i = 0; i < myDataSource.filteredData.length; i++) {
      myDataSource.filteredData[i].picture = 'data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(data[i].picture) as any)
        .changingThisBreaksApplicationSecurity;
      myDataSource.filteredData[i].price = this._price.createMinMaxPrice(data[i].productCostList);
      // @ts-ignore
      myDataSource.filteredData[i].discount = data[i].productCostList.filter(el => el.discount > 0).length > 0 ? '%' : '';
    }
    return myDataSource;
  }




}
