import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterTotalComponent } from './footer-total.component';

describe('FooterTotalComponent', () => {
  let component: FooterTotalComponent;
  let fixture: ComponentFixture<FooterTotalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FooterTotalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterTotalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
