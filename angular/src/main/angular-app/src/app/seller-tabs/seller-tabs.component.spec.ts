import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerTabsComponent } from './seller-tabs.component';

describe('SellerTabsComponent', () => {
  let component: SellerTabsComponent;
  let fixture: ComponentFixture<SellerTabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellerTabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellerTabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
