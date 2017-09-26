import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultorioDetailComponent } from './consultorio-detail.component';

describe('ConsultorioDetailComponent', () => {
  let component: ConsultorioDetailComponent;
  let fixture: ComponentFixture<ConsultorioDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultorioDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultorioDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
