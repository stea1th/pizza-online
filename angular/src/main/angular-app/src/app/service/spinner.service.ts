import {Injectable} from '@angular/core';
import {Overlay} from "@angular/cdk/overlay";
import {MatSpinner} from "@angular/material/progress-spinner";
import {ComponentPortal} from "@angular/cdk/portal";

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  private spinnerTopRef = this.cdkSpinnerCreate();

  constructor(private _overlay: Overlay) {
  }

  cdkSpinnerCreate() {
    return this._overlay.create({
      hasBackdrop: true,
      backdropClass: 'dark-backdrop',
      positionStrategy: this._overlay.position()
        .global()
        .centerHorizontally()
        .centerVertically()
    })
  }

  showSpinner() {
    // let spin = MatSpinner;
    const component = this.spinnerTopRef.attach(new ComponentPortal(MatSpinner));
    // component.instance.color = 'warn';
    // console.log(component.instance.color);
  }

  stopSpinner() {
    this.spinnerTopRef.detach();
  }
}
