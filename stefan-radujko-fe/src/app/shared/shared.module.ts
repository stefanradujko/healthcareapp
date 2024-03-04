import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { NgbNavModule, NgbPaginationModule, NgbToastModule, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import {  HttpClientModule } from '@angular/common/http';
import { GlobalToastComponent } from './components/global-toast/global-toast.component';
import { ConfirmDialogComponent } from "./components/confirm-dialog/confirm-dialog.component";
import { SortableHeaderDirective } from "./directives/sortable-header.directive";
import { HeaderComponent } from "./components/header/header.component";



@NgModule({
  declarations: [
    GlobalToastComponent,
    ConfirmDialogComponent,
    SortableHeaderDirective,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    NgbTooltipModule,
    NgbPaginationModule,
    NgbToastModule,
    NgbNavModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    NgbTooltipModule,
    NgbPaginationModule,
    NgbToastModule,
    NgbNavModule,
    GlobalToastComponent,
    SortableHeaderDirective,
    HeaderComponent
  ]
})
export class SharedModule { }
