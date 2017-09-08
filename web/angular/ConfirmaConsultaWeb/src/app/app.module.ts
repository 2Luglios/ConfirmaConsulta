import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { HttpModule } from '@angular/http';
import { AngularFireAuthModule } from 'angularfire2/auth';
import { AngularFireDatabaseModule } from 'angularfire2/database'; 
import { AngularFireModule } from 'angularfire2';

//Google Material
import { MaterialModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

 
export const firebaseConfig = {
  apiKey: "AIzaSyDmR3P5F8b7KD2omh4RrUfBSNL4cvsEuBU",
  authDomain: "confirmaconsulta-63f26.firebaseapp.com",
  databaseURL: "https://confirmaconsulta-63f26.firebaseio.com",
  projectId: "confirmaconsulta-63f26",
  storageBucket: "confirmaconsulta-63f26.appspot.com",
  messagingSenderId: "42148264085"
};

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AngularFireModule.initializeApp(firebaseConfig),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
}




