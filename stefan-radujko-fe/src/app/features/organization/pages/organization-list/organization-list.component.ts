import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Organization } from 'src/app/models';
import { ConfirmDialogComponent } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { SortableHeaderDirective, SortEvent } from 'src/app/shared/directives/sortable-header.directive';

@Component({
  selector: 'app-organization-list',
  templateUrl: './organization-list.component.html',
  styleUrls: ['./organization-list.component.css']
})
export class OrganizationListComponent implements OnInit {

  organizations?: Organization[];
  currentPage = 1;
  totalItems = 10;
  pageSize = 5;
  sortBy='name';
  sortOrder='asc';
  nameFilter = '';
  orgTypeFilter = '';

  @ViewChildren(SortableHeaderDirective)
  headers?: QueryList<SortableHeaderDirective>

  constructor(private httpOrganization: HttpOrganizationService, private router: Router, private modalService: NgbModal, private toastService: ToastService) { }

  ngOnInit(): void {
    this.loadOrganizations();
  }

  loadOrganizations() {
    if(this.pageSize <= 0){
      this.toastService.showToast('Page size can\'t be 0 or less!', {header:'Finding organizations', className: 'bg-danger text-light'})
      return;
    }
    this.httpOrganization.getByPage(this.currentPage, this.pageSize, this.sortBy, this.sortOrder, this.nameFilter, this.orgTypeFilter).subscribe(
      organizationPage => {
        this.organizations = organizationPage.content;
        this.totalItems = organizationPage.totalElements;
        this.pageSize = organizationPage.size;
      });
  }

  onPageChange(page: number) {
    this.loadOrganizations();
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
    this.loadOrganizations();
  }

  onAddClick(){
    this.router.navigate(['/organization/add']);
  }

  onEditClick(id: number){
    this.router.navigate(['/organization/edit', id]);
  }

  onDetailsClick(id : number){
    this.router.navigate(['/organization/details', id]);
  }

  onFilterClick(){
    this.loadOrganizations();
  }

  onDeleteClick(organization: Organization){
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.message = `Are you sure you want to delete organization <strong>${organization.name}</strong> ?`;
    modalRef.componentInstance.headerText = 'Deleting organization';
    modalRef.result.then((result) => result === 'Ok' && this.deleteOrganization(organization.id));
  }

  deleteOrganization(id: number){
    this.httpOrganization.deleteOrganization(id).subscribe({
      next: res => {
        this.toastService.showToast('Organization deleted successfully!',{header: 'Deleting organization', className: 'bg-success text-light'});
        this.loadOrganizations();
      },
      error: error => this.toastService.showToast('Organization not deleted: Organization has examination/s in progress!', {header:'Deleting organization', className: 'bg-danger text-light'})
    }
    );
  }
}

