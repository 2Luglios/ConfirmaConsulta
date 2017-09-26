import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultorioListComponent } from './consultorio-list.component';

describe('ConsultorioListComponent', () => {
  let component: ConsultorioListComponent;
  let fixture: ComponentFixture<ConsultorioListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultorioListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultorioListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
