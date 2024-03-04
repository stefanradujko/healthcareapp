import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExaminationRoutingModule } from './examination-routing.module';
import { ExaminationAddComponent } from './pages/examination-add/examination-add.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ExaminationListComponent } from './pages/examination-list/examination-list.component';
import { ExaminationDetailsComponent } from './pages/examination-details/examination-details.component';
import { ExaminationEditComponent } from './pages/examination-edit/examination-edit.component';
import { LoginComponent } from '../login/login.component';


@NgModule({
  declarations: [
    ExaminationAddComponent,
    ExaminationListComponent,
    ExaminationDetailsComponent,
    ExaminationEditComponent,
    LoginComponent,

  ],
  imports: [
    CommonModule,
    ExaminationRoutingModule,
    SharedModule
  ]
})
export class ExaminationModule { }
