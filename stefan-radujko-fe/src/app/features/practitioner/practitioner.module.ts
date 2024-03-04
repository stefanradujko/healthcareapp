import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PractitionerRoutingModule } from './practitioner-routing.module';
import { PractitionerListComponent } from './pages/practitioner-list/practitioner-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PractitionerEditComponent } from './pages/practitioner-edit/practitioner-edit.component';
import { PractitionerAddComponent } from './pages/practitioner-add/practitioner-add.component';
import { PractitionerDetailsComponent } from './pages/practitioner-details/practitioner-details.component';


@NgModule({
  declarations: [
    PractitionerListComponent,
    PractitionerEditComponent,
    PractitionerAddComponent,
    PractitionerDetailsComponent
  ],
  imports: [
    CommonModule,
    PractitionerRoutingModule,
    SharedModule
  ]
})
export class PractitionerModule { }
