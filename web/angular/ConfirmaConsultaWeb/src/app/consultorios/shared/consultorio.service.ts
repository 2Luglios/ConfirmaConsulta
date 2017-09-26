import { Injectable } from '@angular/core';
import { FirebaseListObservable, FirebaseObjectObservable, AngularFireDatabase } from 'angularfire2/database';
import { Consultorio } from './consultorio';

@Injectable()
export class ConsultorioService {

  private basePath: string = '/consultorios';
  consultorios: FirebaseListObservable<Consultorio[]> = null; //  list of objects
  consultorio: FirebaseObjectObservable<Consultorio> = null; //   single object
  constructor(private db: AngularFireDatabase) { }

  getConsultoriosList(query={}): FirebaseListObservable<Consultorio[]> {
    this.consultorios = this.db.list(this.basePath, {
      query: query
    });
    return this.consultorios
  }
  // Return a single observable item
  getConsultorio(key: string): FirebaseObjectObservable<Consultorio> {
    const consultorioPath =  `${this.basePath}/${key}`;
    this.consultorio = this.db.object(consultorioPath)
    return this.consultorio
  }

  createConsultorio(consultorio: Consultorio): void  {
    this.consultorios.push(consultorio)
      .catch(error => this.handleError(error))
  }
  // Update an existing item
  updateConsultorio(key: string, value: any): void {
    this.consultorios.update(key, value)
      .catch(error => this.handleError(error))
  }
  // Deletes a single item
  deleteConsultorio(key: string): void {
      this.consultorios.remove(key)
        .catch(error => this.handleError(error))
  }
  // Deletes the entire list of items
  deleteAll(): void {
      this.consultorios.remove()
        .catch(error => this.handleError(error))
  }
  // Default error handling for all actions
  private handleError(error) {
    console.log(error)
  }
  

}
