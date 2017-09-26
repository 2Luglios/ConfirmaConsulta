import { Component, OnInit } from '@angular/core';
import { FirebaseListObservable, FirebaseObjectObservable, AngularFireDatabase } from 'angularfire2/database';
import { Consultorio } from '../shared/consultorio';
import { ConsultorioService } from '../shared/consultorio.service';

@Component({
  selector: 'app-consultorio-list',
  templateUrl: './consultorio-list.component.html',
  styleUrls: ['./consultorio-list.component.css']
})
export class ConsultorioListComponent implements OnInit {

  public consultorios: FirebaseListObservable<Consultorio[]>;

  constructor(private consultorioSvc: ConsultorioService) { }

  ngOnInit() {
    this.consultorios = this.consultorioSvc.getConsultoriosList({limitToLast: 5})
    this.consultorios.subscribe()
  }
  
  deleteConsultorios() {
    this.consultorioSvc.deleteAll()
  }
}
