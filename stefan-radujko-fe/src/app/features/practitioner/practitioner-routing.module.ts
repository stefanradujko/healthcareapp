import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PractitionerAddComponent } from './pages/practitioner-add/practitioner-add.component';
import { PractitionerDetailsComponent } from './pages/practitioner-details/practitioner-details.component';
import { PractitionerEditComponent } from './pages/practitioner-edit/practitioner-edit.component';
import { PractitionerListComponent } from './pages/practitioner-list/practitioner-list.component';

const routes: Routes = [
  {path: 'list', component: PractitionerListComponent},
  {path: 'add', component: PractitionerAddComponent},
  {path: 'edit/:id', component: PractitionerEditComponent},
  {path: 'details/:id', component: PractitionerDetailsComponent},
  {path: '', redirectTo:'list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PractitionerRoutingModule { }
