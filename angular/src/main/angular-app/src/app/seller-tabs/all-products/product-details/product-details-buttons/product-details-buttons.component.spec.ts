import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDetailsButtonsComponent } from './product-details-buttons.component';

describe('ProductDetailsButtonsComponent', () => {
  let component: ProductDetailsButtonsComponent;
  let fixture: ComponentFixture<ProductDetailsButtonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductDetailsButtonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDetailsButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
