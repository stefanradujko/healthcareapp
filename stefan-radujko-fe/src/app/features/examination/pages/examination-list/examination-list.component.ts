import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpServiceTypeService } from 'src/app/core/services/http-service-type.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Examination, ServiceType } from 'src/app/models';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-examination-list',
  templateUrl: './examination-list.component.html',
  styleUrls: ['./examination-list.component.css']
})
export class ExaminationListComponent implements OnInit {

  examinations?: Examination[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy ='status';
  sortOrder = 'asc';
  organizationFilter='';
  nameFilter='';
  surnameFilter='';
  serviceTypeFilter='';
  statusFilter='';
  priorityFilter='';
  records: ServiceType[] = [];

  @ViewChild('csvReader') csvReader: any;


  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>

  constructor(private httpExamination: HttpExaminationService, private httpServiceType: HttpServiceTypeService, private router: Router, private modalService: NgbModal, private toastService: ToastService) { }

  ngOnInit(): void {
  }

  loadExaminations() {
    if(this.pageSize <= 0){
      this.toastService.showToast('Page size can\'t be 0 or less!', {header:'Finding examinations', className: 'bg-danger text-light'})
      return;
    }
    this.httpExamination.getByPage(this.currentPage, this.pageSize, this.sortBy, this.sortOrder,
      this.organizationFilter, this.nameFilter, this.surnameFilter, this.serviceTypeFilter,
      this.statusFilter, this.priorityFilter).subscribe(
      examinationPage => {
        this.examinations = examinationPage.content;
        this.totalItems = examinationPage.totalElements;
        this.pageSize = examinationPage.size;
      });
  }

  onPageChange(page: number) {
    this.loadExaminations();
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
    this.loadExaminations();
  }

  onAddClick(){
    this.router.navigate(['/examination/add']);
  }

  onEditClick(id : number){
    this.router.navigate(['/examination/edit', id]);
  }

  onDetailsClick(id : number){
    this.router.navigate(['/examination/details', id]);
  }

  onFilterClick(){
    if(this.organizationFilter == '' && this.nameFilter == '' && this.surnameFilter == '' && this.serviceTypeFilter == ''
    && this.statusFilter == '' && this.priorityFilter == ''){
      this.toastService.showToast('You must enter at least 1 filter!',{header: 'Finding examinations', className: 'bg-danger text-light'});
      return;
    }
    this.loadExaminations();
  }

  onDeleteClick(examination: Examination){
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete examination of organization
    <strong>${examination.organization.name}</strong> for patient
    <strong>${examination.patient.name} ${examination.patient.surname}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting examination';
    modalRef.result.then((result) => result === 'Ok' && this.deleteExamination(examination.id));
  }

  deleteExamination(id: number){
    this.httpExamination.deleteExamination(id).subscribe((response) => {
      this.toastService.showToast('Examination deleted successfully!',{header: 'Deleting examination', className: 'bg-success text-light'});
      this.loadExaminations();
    });
  }

  uploadCsv($event: any){
    let files = $event.srcElement.files;

    if (this.isValidCSVFile(files[0])) {
      let input = $event.target;
      let reader = new FileReader();
      reader.readAsText(input.files[0]);

      reader.onload = () => {
        let csvData = reader.result;
        let csvRecordsArray = (<string>csvData).split(/\r\n|\n/);
        console.log(csvRecordsArray);
        let headersRow = this.getHeaderArray(csvRecordsArray);
        console.log('HR: ', headersRow);
        this.records = this.getDataRecordsArrayFromCSVFile(csvRecordsArray, headersRow.length);
        this.saveServiceTypes();
      };
    } else {
      alert("Please import valid .csv file.");
      this.fileReset();
    }
  }

  getDataRecordsArrayFromCSVFile(csvRecordsArray: any, headerLength: any) {
    let csvArr = [];

    for (let i = 1; i < csvRecordsArray.length; i++) {
      let row = (<string>csvRecordsArray[i]).split(',');
      console.log('ROW: ', row);
      if (row.length == headerLength) {
        let csvRecord: ServiceType = {} as ServiceType;
        console.log(csvRecord);
        csvRecord.id = Number(row[0].trim());
        csvRecord.identifier = row[1].trim();
        csvRecord.name = row[2].trim();
        csvArr.push(csvRecord);
      }
    }
    console.log('CSVARR: ', csvArr);
    return csvArr;
  }

  isValidCSVFile(file: any) {
    return file.name.endsWith(".csv");
  }

  getHeaderArray(csvRecordsArr: any) {
    let headers = (<string>csvRecordsArr[0]).split(',');
    let headerArray = [];
    for (let j = 0; j < headers.length; j++) {
      headerArray.push(headers[j]);
    }
    console.log('HA: ', headerArray);
    return headerArray;
  }

  fileReset() {
    this.csvReader.nativeElement.value = "";
    this.records = [];
  }

  saveServiceTypes(){
    console.log('RE: ', this.records);
    this.httpServiceType.saveAll(this.records).subscribe({
      next: res => this.toastService.showToast('Service types loaded successfully!', {header:'Reading CSV', className: 'bg-success text-light'}),
      error: error => this.toastService.showToast('Service types not loaded!', {header:'Reading CSV', className: 'bg-danger text-light'})
    }
    );
  }

}
