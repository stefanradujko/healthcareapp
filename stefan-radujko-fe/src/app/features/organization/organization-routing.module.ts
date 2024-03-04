import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrganizationAddComponent } from './pages/organization-add/organization-add.component';
import { OrganizationDetailsComponent } from './pages/organization-details/organization-details.component';
import { OrganizationEditComponent } from './pages/organization-edit/organization-edit.component';
import { OrganizationListComponent } from './pages/organization-list/organization-list.component';

const routes: Routes = [
  {path: 'list', component: OrganizationListComponent},
  {path: 'add', component: OrganizationAddComponent},
  {path: 'edit/:id', component: OrganizationEditComponent},
  {path: 'details/:id', component: OrganizationDetailsComponent},
  {path: '', redirectTo:'list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrganizationRoutingModule { }
