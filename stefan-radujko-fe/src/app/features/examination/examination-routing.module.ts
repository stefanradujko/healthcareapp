import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExaminationAddComponent } from './pages/examination-add/examination-add.component';
import { ExaminationDetailsComponent } from './pages/examination-details/examination-details.component';
import { ExaminationEditComponent } from './pages/examination-edit/examination-edit.component';
import { ExaminationListComponent } from './pages/examination-list/examination-list.component';

const routes: Routes = [
  {path: 'list', component: ExaminationListComponent},
  {path: 'add', component: ExaminationAddComponent},
  {path: 'details/:id', component: ExaminationDetailsComponent},
  {path: 'edit/:id', component: ExaminationEditComponent},
  {path: '', redirectTo:'list', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExaminationRoutingModule { }
