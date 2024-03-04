import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrganizationRoutingModule } from './organization-routing.module';
import { OrganizationAddComponent } from './pages/organization-add/organization-add.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { OrganizationDetailsComponent } from './pages/organization-details/organization-details.component';
import { OrganizationListComponent } from './pages/organization-list/organization-list.component';
import { OrganizationEditComponent } from './pages/organization-edit/organization-edit.component';


@NgModule({
  declarations: [
    OrganizationAddComponent,
    OrganizationDetailsComponent,
    OrganizationListComponent,
    OrganizationEditComponent
  ],
  imports: [
    CommonModule,
    OrganizationRoutingModule,
    SharedModule
  ]
})
export class OrganizationModule { }
