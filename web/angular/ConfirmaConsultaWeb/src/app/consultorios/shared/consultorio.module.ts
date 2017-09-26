import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AngularFireAuthModule } from 'angularfire2/auth';
import { AngularFireDatabaseModule } from 'angularfire2/database'; 
import { AngularFireModule } from 'angularfire2';

//Google Material
import { MaterialModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConsultorioListComponent } from '../consultorio-list/consultorio-list.component';
import { ConsultorioDetailComponent } from '../consultorio-detail/consultorio-detail.component';
import { ConsultorioFormComponent } from '../consultorio-form/consultorio-form.component';
import { ConsultorioService } from '../shared/consultorio.service';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    AngularFireDatabaseModule
  ],
  declarations: [
    ConsultorioListComponent,
    ConsultorioDetailComponent,
    ConsultorioFormComponent
  ],
  providers: [
    ConsultorioService
  ]
})
export class ConsultorioModule { }