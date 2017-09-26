import { Component, OnInit, Input } from '@angular/core';
import { Consultorio } from '../shared/consultorio';
import { ConsultorioService } from '../shared/consultorio.service';

@Component({
  selector: 'app-consultorio-detail',
  templateUrl: './consultorio-detail.component.html',
  styleUrls: ['./consultorio-detail.component.css']
})
export class ConsultorioDetailComponent implements OnInit {
  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  @Input() consultorio: Consultorio;
  
  constructor(private consultorioSvc: ConsultorioService) { }
  deleteItem() {
    this.consultorioSvc.deleteConsultorio(this.consultorio.$hash)
  }
}
