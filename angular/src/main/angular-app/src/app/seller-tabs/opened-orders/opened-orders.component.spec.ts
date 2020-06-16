import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenedOrdersComponent } from './opened-orders.component';

describe('OpenedOrdersComponent', () => {
  let component: OpenedOrdersComponent;
  let fixture: ComponentFixture<OpenedOrdersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenedOrdersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenedOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
