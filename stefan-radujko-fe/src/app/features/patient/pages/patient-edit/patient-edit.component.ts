import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Organization } from 'src/app/models';
import { Patient } from 'src/app/models/patient.model';
import { Practitioner } from 'src/app/models/practitioner.model';


@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css']
})
export class PatientEditComponent implements OnInit {
  patient: Patient = {} as Patient;
  nameSurname: string = '';


  constructor(private httpPatient: HttpPatientService, private httpOrganization: HttpOrganizationService, private httpPractitioner: HttpPractitionerService, private activeRoute: ActivatedRoute, private toastService: ToastService, private router: Router) { }

  ngOnInit(): void {
    this.patient.organization = {} as Organization;
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    console.log('id', id);
    this.httpPatient.getPatient(id).subscribe(res => this.setPatient(res));
  }

  setPatient(res : Patient){
    this.patient = res;
    if(this.patient.practitioner != null){
      this.nameSurname = res.practitioner.name + " " + res.practitioner.surname;
    }
    console.log(this.patient);
  }

  findOrganizationClick(){
    console.log(this.patient.organization);
    this.httpOrganization.getOrganizationByName(String(this.patient.organization.name)).subscribe({
      next: organization => {
        this.assignOrganization(organization)
      },
      error: error => this.toastService.showToast('Organization with that name doesn\'t exist!', {header:'Finding organization', className: 'bg-danger text-light'})

    }
    );
  }

  assignOrganization(organization: Organization){
    this.patient.organization = organization;
    this.nameSurname = '';
    this.patient.practitioner = {} as Practitioner;
    this.toastService.showToast('Organization found successfully!', {header:'Finding organization', className: 'bg-success text-light'})
    console.log(this.patient.organization);
  }

  findPractitionerClick(){
    var nameSurnameSplit = this.nameSurname.split (" ");
    console.log(nameSurnameSplit[0], nameSurnameSplit[1]);
    this.httpPractitioner.getPractitionerByFullNameAndOrg(nameSurnameSplit[0], nameSurnameSplit[1], this.patient.organization.id).subscribe({
      next: practitioner => {
        this.assignPractitioner(practitioner)
      },
      error: error => this.toastService.showToast('Practitioner with that full name doesn\'t exist in selected organization!', {header:'Finding practitioner', className: 'bg-danger text-light'})
    }

    );
  }

  assignPractitioner(practitioner: Practitioner){
    if(practitioner.qualification != 'Doctor of Medicine'){
      this.toastService.showToast('Practitioner is not qualified as Doctor of Medicine!', {header:'Finding practitioner', className: 'bg-danger text-light'})
      return;
    }
    this.patient.practitioner = practitioner;
    this.toastService.showToast('Practitioner found successfully!', {header:'Finding practitioner', className: 'bg-success text-light'})
    console.log(this.patient.practitioner);
  }

  editPatient(){
    console.log(this.patient);
    if(this.patient.organization.name == null){
      this.toastService.showToast('Patient not updated: Organization is required!', {header:'Updating patient', className: 'bg-danger text-light'})
      return;
    }
    if(this.patient.practitioner.name == null){
      this.toastService.showToast('Patient not updated: Practitioner is required!', {header:'Updating patient', className: 'bg-danger text-light'})
      return;
    }
    if(new Date() < new Date(this.patient.birthDate)){
      this.toastService.showToast('Patient not updated: Birth date can\'t be in the future!', {header:'Updating patient', className: 'bg-danger text-light'})
      return;
    }
    this.httpPatient.updatePatient(this.patient).subscribe({
      next: res => {
        this.checkIfPatientIsUpdated(res)
      },
      error: error => this.toastService.showToast('Patient not updated: Patient with that identifier already exists!', {header:'Updating patient', className: 'bg-danger text-light'})
    }
    );
  }

  checkIfPatientIsUpdated(res: Patient){
    if(res.hasOwnProperty('id')){
      this.toastService.showToast('Patient updated successfully!', {header:'Updating patient', className: 'bg-success text-light'});
      this.router.navigate(['/patient']);
    } else{
      this.toastService.showToast('Patient not updated: ' + this.getMessage(res), {header:'Updating patient', className: 'bg-danger text-light'});
    }
  }

  getMessage(res: Patient){
    console.log(res);
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
    if(res.hasOwnProperty('organization')){
      return res.organization;
    }
    if(res.hasOwnProperty('practitioner')){
      return res.practitioner;
    }
    return res.email;
  }
}
