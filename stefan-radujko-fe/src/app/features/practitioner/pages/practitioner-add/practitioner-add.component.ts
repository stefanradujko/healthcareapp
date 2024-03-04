import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Organization } from 'src/app/models';
import { Practitioner } from 'src/app/models/practitioner.model';

@Component({
  selector: 'app-practitioner-add',
  templateUrl: './practitioner-add.component.html',
  styleUrls: ['./practitioner-add.component.css']
})
export class PractitionerAddComponent implements OnInit {

  practitioner: Practitioner = {} as Practitioner;
  organizations?: Organization[];

  constructor(private httpPractitioner: HttpPractitionerService, private httpOrganization: HttpOrganizationService, private router: Router, private toastService: ToastService) { }

  ngOnInit(): void {
    this.httpOrganization.getAll().subscribe(organizations => this.organizations = organizations);
  }

  async savePractitioner(){
    this.practitioner.active = true;
    this.practitioner.id = 0;
    if(this.practitioner.organization == null){
      this.toastService.showToast('Practitioner not saved: Organization is required!', {header:'Saving practitioner', className: 'bg-danger text-light'});
      return;
    }
    let today = new Date();
    console.log(new Date(this.practitioner.birthDate).getFullYear() - today.getFullYear());
    if(today.getFullYear() - new Date(this.practitioner.birthDate).getFullYear()  < 18 ){
      this.toastService.showToast('Practitioner not saved: Practitioner must be at least 18 years old!', {header:'Saving practitioner', className: 'bg-danger text-light'})
      return;
    }
    if(this.practitioner.username == null){
      this.practitioner.username = '';
    }
    if(this.practitioner.password == null){
      this.practitioner.password = '';
    }
    if((this.practitioner.username != '' && this.practitioner.password == '') ||
     (this.practitioner.username == '' && this.practitioner.password != '')){
      this.toastService.showToast('Practitioner not saved: If added, both username and password must be filled!', {header:'Saving practitioner', className: 'bg-danger text-light'})
      return;
    }else if(this.practitioner.username == '' && this.practitioner.password == ''){
      this.practitioner.username = null;
      this.practitioner.password = null;
    }

    const id = Number(this.practitioner.organization);
    this.practitioner.organization = await firstValueFrom(this.httpOrganization.getOrganization(id));
    console.log(this.practitioner.organization);
    this.httpPractitioner.savePractitioner(this.practitioner).subscribe(
      {
        next: res => {
          this.checkIfPractitionerIsSaved(res)
        },
        error: error => this.toastService.showToast('Practitioner not saved: Practitioner with that identifier already exists!', {header:'Saving practitioner', className: 'bg-danger text-light'})
      }
    );
  }

  checkIfPractitionerIsSaved(res: Practitioner){
    if(res.hasOwnProperty('id')){
      this.toastService.showToast('Practitioner saved successfully!', {header:'Saving practitioner', className: 'bg-success text-light'});
      this.router.navigate(['/practitioner']);
    } else{
      this.toastService.showToast('Practitioner not saved: ' + this.getMessage(res), {header:'Saving practitioner', className: 'bg-danger text-light'});
    }
  }

  getMessage(res: Practitioner){
    if(res.hasOwnProperty('identifier')){
      return res.identifier;
    }
    if(res.hasOwnProperty('name')){
      return res.name;
    }
    if(res.hasOwnProperty('surname')){
      return res.surname;
    }
    if(res.hasOwnProperty('qualification')){
      return res.qualification;
    }
    if(res.hasOwnProperty('birthDate')){
      return res.birthDate;
    }
    return res.email;
  }
}
