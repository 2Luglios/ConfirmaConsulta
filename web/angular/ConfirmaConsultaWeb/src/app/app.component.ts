import { Component } from '@angular/core';

import { AngularFireDatabase, FirebaseListObservable, FirebaseObjectObservable } from 'angularfire2/database';
import { AngularFireAuth } from 'angularfire2/auth';
import { Observable } from 'rxjs/Observable';
import * as firebase from 'firebase/app';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']

})
export class AppComponent {
  title = 'My First Angular App Using Firebase';

  usuarios: FirebaseObjectObservable<any[]>;
  mensagens: FirebaseListObservable<any[]>;
  especialidades: FirebaseListObservable<any[]>;
  constructor(db: AngularFireDatabase) {
    this.mensagens = db.list('/mensagens');
    this.usuarios = db.object('/usuarios');
    this.especialidades = db.list('/especialidades', {
      query: {
        orderByValue: true,
        limitToFirst: 6
      }
    });

    var connectedRef = firebase.database().ref(".info/connected");
    connectedRef.on("value", function(snap) {
      if (snap.val() === true) {
        alert("connected");
      } else {
        alert("not connected");
      }
    });

  }
  

  
  
}