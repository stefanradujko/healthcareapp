import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Organization } from 'src/app/models';
import { Practitioner } from 'src/app/models/practitioner.model';


@Component({
  selector: 'app-practitioner-edit',
  templateUrl: './practitioner-edit.component.html',
  styleUrls: ['./practitioner-edit.component.css']
})
export class PractitionerEditComponent implements OnInit {

  practitioner: Practitioner = {} as Practitioner;
  organizations?: Organization[];
  password : string = '';

  constructor(private httpPractitioner: HttpPractitionerService,private httpOrganization : HttpOrganizationService, private activeRoute: ActivatedRoute, private toastService: ToastService, private router: Router) { }

  ngOnInit(): void {
    this.practitioner.organization = {} as Organization;
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    console.log('id', id);
    this.httpPractitioner.getPractitioner(id).subscribe(practitioner => this.practitioner = practitioner);
    this.httpOrganization.getAll().subscribe(organizations => this.organizations = organizations);
  }

  editPractitioner(){
    console.log(this.practitioner);
    let today = new Date();
    if(today.getFullYear() - new Date(this.practitioner.birthDate).getFullYear()  < 18 ){
      this.toastService.showToast('Practitioner not updated: Practitioner must be at least 18 years old!', {header:'Updating practitioner', className: 'bg-danger text-light'})
      return;
    }
    if(this.practitioner.username == null){
      this.practitioner.username = '';
    }
    this.practitioner.password = this.password;
    if((this.practitioner.username != '' && this.practitioner.password == '') ||
     (this.practitioner.username == '' && this.practitioner.password != '')){
      this.toastService.showToast('Practitioner not saved: If added, both username and password must be filled!', {header:'Saving practitioner', className: 'bg-danger text-light'})
      return;
    }else if(this.practitioner.username == '' && this.practitioner.password == ''){
      this.practitioner.username = null;
      this.practitioner.password = null;
    }
    this.httpPractitioner.updatePractitioner(this.practitioner).subscribe(
      {
        next: res => {
          this.checkIfPractitionerIsUpdated(res)
        },
        error: error => this.toastService.showToast('Practitioner not updated: Practitioner with that identifier already exists!', {header:'Updating practitioner', className: 'bg-danger text-light'})
      }
    );
  }

  checkIfPractitionerIsUpdated(res: Practitioner){
    if(res.hasOwnProperty('id')){
      this.toastService.showToast('Practitioner updated successfully!', {header:'Updating practitioner', className: 'bg-success text-light'});
      this.router.navigate(['/practitioner']);
    } else{
      this.toastService.showToast('Practitioner not updated: ' + this.getMessage(res), {header:'Updating practitioner', className: 'bg-danger text-light'});
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
    if(res.hasOwnProperty('birthDate')){
      return res.birthDate;
    }
    return res.email;
  }
}
