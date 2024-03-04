import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientAddComponent } from './pages/patient-add/patient-add.component';
import { PatientDetailsComponent } from './pages/patient-details/patient-details.component';
import { PatientEditComponent } from './pages/patient-edit/patient-edit.component';
import { PatientListComponent } from './pages/patient-list/patient-list.component';

const routes: Routes = [
  {path: 'list', component: PatientListComponent},
  {path: 'add', component: PatientAddComponent},
  {path: 'edit/:id', component: PatientEditComponent},
  {path: 'details/:id', component: PatientDetailsComponent},
  {path: '', redirectTo:'list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
