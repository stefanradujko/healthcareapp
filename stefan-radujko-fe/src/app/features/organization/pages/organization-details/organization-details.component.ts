import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom, ignoreElements } from 'rxjs';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { Organization, Practitioner } from 'src/app/models';

@Component({
  selector: 'app-organization-details',
  templateUrl: './organization-details.component.html',
  styleUrls: ['./organization-details.component.css']
})
export class OrganizationDetailsComponent implements OnInit {

  organization?: Organization;
  examinationsCount?: number;
  examsInProgressCount?: number;
  practitionersCount?: number;
  practitionersListDisplay = '';

  constructor(private httpOrganization : HttpOrganizationService, private httpExamination: HttpExaminationService,
    private httpPractitioner: HttpPractitionerService, private activeRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    console.log('id', id);
    this.fetchOrganization(id);
    this.fetchAdditionalData(id);
  }

  async fetchOrganization(id: number){
    this.organization = await firstValueFrom(this.httpOrganization.getOrganization(id));
    this.setMissingValues();
  }

  setMissingValues(){
    console.log(this.organization);
    if(this.organization?.identifier == null || this.organization?.identifier == ''){
      this.organization!.identifier = 'N/A';
    }
    if(this.organization?.adress == null || this.organization?.adress == ''){
      this.organization!.adress = 'N/A';
    }
    if(this.organization?.email == null || this.organization?.email == ''){
      this.organization!.email= 'N/A';
    }
    if(this.organization?.phone == null || this.organization.phone == ''){
      this.organization!.phone = 'N/A';
    }
  }

  fetchAdditionalData(id : number){
    this.httpExamination.getExamCountForOrg(id).subscribe(res => this.examinationsCount = res);
    this.httpExamination.getExamInProgressCountForOrg(id).subscribe(res => this.examsInProgressCount = res);
    this.httpPractitioner.getPractCountForOrg(id).subscribe(res => this.practitionersCount = res);
    this.httpPractitioner.getPractForOrg(id).subscribe(res => this.setPractitionersDisplay(res));
  }

  setPractitionersDisplay(res: Practitioner[]){
    for(let i = 0; i < res.length; i++){
      this.practitionersListDisplay += res[i].name + ' ' +
      res[i].surname + ' - ' + res[i].qualification + '\n' ;
    }
  }

}
