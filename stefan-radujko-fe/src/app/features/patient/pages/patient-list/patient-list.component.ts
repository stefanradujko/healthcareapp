import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Patient } from 'src/app/models/patient.model';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  patients?: Patient[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy='name';
  sortOrder='asc';
  nameFilter='';
  surnameFilter='';
  organizationFilter='';
  practNameFilter='';
  practSurnameFilter='';
  unassignedFilter = false;

  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>

  constructor(private httpPatient: HttpPatientService, private router: Router, private modalService: NgbModal, private toastService: ToastService) { }

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients() {
    if(this.pageSize <= 0){
      this.toastService.showToast('Page size can\'t be 0 or less!', {header:'Finding patients', className: 'bg-danger text-light'})
      return;
    }
    this.httpPatient.getByPage(this.currentPage, this.pageSize, this.sortBy, this.sortOrder,
      this.nameFilter, this.surnameFilter, this.organizationFilter, this.practNameFilter, this.practSurnameFilter, this.unassignedFilter).subscribe(
      patientPage => {
        this.patients = patientPage.content;
        this.totalItems = patientPage.totalElements;
        this.pageSize = patientPage.size;
      });
  }

  onPageChange(page: number) {
    this.loadPatients();
  }


  onSort(sortEvent: SortEvent) {
    console.log('sort event:', sortEvent);
    this.sortBy = sortEvent.column;
    this.sortOrder = sortEvent.direction;
    this.headers?.forEach( header => {
      if (header.sortable !== sortEvent.column) {
        header.direction = '';
      }
    })
    this.loadPatients();
  }

  onAddClick(){
    this.router.navigate(['/patient/add']);
  }

  onEditClick(id: number){
    this.router.navigate(['/patient/edit', id]);
  }

  onDetailsClick(id: number){
    this.router.navigate(['/patient/details', id]);
  }

  onFilterClick(){
    this.loadPatients();
  }

  onUnassignedClick(){
    if(this.unassignedFilter){
      this.unassignedFilter = false;
    } else{
      this.unassignedFilter = true;
    }
  }

  onDeleteClick(patient: Patient){
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete patient <strong>${patient.name} ${patient.surname}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting patient';
    modalRef.result.then((result) => result === 'Ok' && this.deletePatient(patient.id));
  }

  deletePatient(id: number){
    this.httpPatient.deletePatient(id).subscribe({
      next: res => {
        this.toastService.showToast('Patient deleted successfully!',{header: 'Deleting patient', className: 'bg-success text-light'});
        this.loadPatients();
      },
      error: error => this.toastService.showToast('Patient not deleted: Patient has examination/s in progress!', {header:'Deleting organization', className: 'bg-danger text-light'})
    }
    );
  }
}
