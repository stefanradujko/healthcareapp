import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientRoutingModule } from './patient-routing.module';
import { PatientAddComponent } from './pages/patient-add/patient-add.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PatientListComponent } from './pages/patient-list/patient-list.component';
import { PatientDetailsComponent } from './pages/patient-details/patient-details.component';
import { PatientEditComponent } from './pages/patient-edit/patient-edit.component';


@NgModule({
  declarations: [
    PatientAddComponent,
    PatientListComponent,
    PatientDetailsComponent,
    PatientEditComponent
  ],
  imports: [
    CommonModule,
    PatientRoutingModule,
    SharedModule
  ]
})
export class PatientModule { }
