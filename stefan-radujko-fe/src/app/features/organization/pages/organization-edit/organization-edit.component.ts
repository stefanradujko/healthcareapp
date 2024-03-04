import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpOrganizationTypeService } from 'src/app/core/services/http-organization-type.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Organization, OrganizationType } from 'src/app/models';

@Component({
  selector: 'app-organization-edit',
  templateUrl: './organization-edit.component.html',
  styleUrls: ['./organization-edit.component.css']
})
export class OrganizationEditComponent implements OnInit {

  organization: Organization = {} as Organization;
  orgTypes?: OrganizationType[];

  constructor(private httpOrganization : HttpOrganizationService, private httpOrgType: HttpOrganizationTypeService, private activeRoute: ActivatedRoute, private toastService: ToastService, private router: Router) { }

  ngOnInit(): void {
    this.organization.organizationType = {} as OrganizationType;
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    console.log('id', id);
    this.httpOrganization.getOrganization(id).subscribe(org => this.organization = org);
    this.httpOrgType.getAll().subscribe(orgTypes => this.orgTypes = orgTypes);
  }

  editOrganization(){
    console.log(this.organization?.organizationType)
    this.httpOrganization.updateOrganization(this.organization!).subscribe(
      {
        next: res => {
          this.checkIfOrganizationIsUpdated(res)
        },
        error: error => this.toastService.showToast('Organization not updated: Organization with that identifier already exists!', {header:'Updating organization', className: 'bg-danger text-light'})
      }
    );
  }

  checkIfOrganizationIsUpdated(res: Organization){
    if(res.hasOwnProperty('id')){
      this.toastService.showToast('Organization updated successfully!', {header:'Updating organization', className: 'bg-success text-light'});
      this.router.navigate(['/organization']);
    } else{
      this.toastService.showToast('Organization not updated: ' + this.getMessage(res), {header:'Updating organization', className: 'bg-danger text-light'});
    }
  }

  getMessage(res: Organization){
    if(res.hasOwnProperty('identifier')){
      return res.identifier;
    }
    if(res.hasOwnProperty('name')){
      return res.name;
    }
    return res.email;
  }

}
