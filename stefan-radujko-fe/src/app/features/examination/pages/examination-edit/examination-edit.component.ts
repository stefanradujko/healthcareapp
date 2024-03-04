import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { HttpExaminationPractitionerService } from 'src/app/core/services/http-examination-practitioner.service';
import { HttpExaminationService } from 'src/app/core/services/http-examination.service';
import { HttpOrganizationService } from 'src/app/core/services/http-organization.service';
import { HttpPatientService } from 'src/app/core/services/http-patient.service';
import { HttpPractitionerService } from 'src/app/core/services/http-practitioner.service';
import { HttpServiceTypeService } from 'src/app/core/services/http-service-type.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Examination, Organization, Patient, Practitioner, ServiceType } from 'src/app/models';
import { ExaminationPractitioner } from 'src/app/models/examination-practitioner.model';


@Component({
  selector: 'app-examination-edit',
  templateUrl: './examination-edit.component.html',
  styleUrls: ['./examination-edit.component.css']
})
export class ExaminationEditComponent implements OnInit {
  examination : Examination = {} as Examination;
  patientNameSurname : string = '';
  practitionerNameSurname : string = '';
  serviceTypes?: ServiceType[];
  examinationPractitioners?: ExaminationPractitioner[] = [];
  practitionersListDisplay: string = ''; //string for list of practitioners on form


  constructor(private httpExamination: HttpExaminationService, private httpExamPract: HttpExaminationPractitionerService,
    private httpPatient: HttpPatientService , private httpOrganization: HttpOrganizationService,
    private httpServiceType: HttpServiceTypeService, private httpPractitioner: HttpPractitionerService,
    private activeRoute: ActivatedRoute,private router: Router, private toastService: ToastService) { }

  ngOnInit(): void {
    const id = Number(this.activeRoute.snapshot.paramMap.get('id'));
    console.log('id', id);
    this.examination.organization = {} as Organization;
    this.examination.patient = {} as Patient;
    this.examination.serviceType = {} as ServiceType;
    this.fetchServiceTypes();
    this.fetchExaminationAndPractitioners(id);
  }

  async fetchExaminationAndPractitioners(id: number){
    this.examination = await firstValueFrom(this.httpExamination.getExamination(id));
    this.patientNameSurname = this.examination.patient.name + ' ' + this.examination.patient.surname;
    this.httpExamPract.getExamPractByExamId(this.examination.id).subscribe(res => this.fetchedExaminationPractitioners(res));
  }

  fetchedExaminationPractitioners(res : ExaminationPractitioner[]){
    this.examinationPractitioners = res;
    this.reloadPractitionerListDisplay();
  }

  fetchServiceTypes(){
    this.httpServiceType.getAll().subscribe(serviceTypes => this.serviceTypes = serviceTypes);
  }

  findPatientClick(){
    var nameSurnameSplit = this.patientNameSurname.split (" ");
    this.httpPatient.getPatientByFullName(nameSurnameSplit[0], nameSurnameSplit[1]).subscribe({
      next: patient => {
        this.assignPatient(patient)
      },
      error: error => this.toastService.showToast('Patient with that full name doesn\'t exist!', {header:'Finding patient', className: 'bg-danger text-light'})
    }
    );
  }

  assignPatient(patient: Patient){
    if(patient.deceased == true){
      this.toastService.showToast('Patient is deceased!', {header:'Finding patient', className: 'bg-danger text-light'})
      return;
    }
    this.examination.patient = patient;
    this.examination.organization = patient.organization; //assign patients default organization
    if(patient.practitioner.name != null){
      this.practitionerNameSurname = patient.practitioner.name + " " + patient.practitioner.surname; //show default practitioner's full name on form
    }
    this.toastService.showToast('Patient found successfully!', {header:'Finding patient', className: 'bg-success text-light'})
  }

  findOrganizationClick(){
    console.log(this.examination.organization);
    this.httpOrganization.getOrganizationByName(String(this.examination.organization.name)).subscribe({
      next: organization => {
        this.assignOrganization(organization)
      },
      error: error => this.toastService.showToast('Organization with that name doesn\'t exist!', {header:'Finding organization', className: 'bg-danger text-light'})

    }
    );
  }

  assignOrganization(organization: Organization){
    this.examination.organization = organization;
    this.examinationPractitioners = [];
    this.practitionersListDisplay = '';
    this.toastService.showToast('Organization found successfully!', {header:'Finding organization', className: 'bg-success text-light'})
    console.log(this.examination.organization);
  }

  findPractitionerClick(){
    var nameSurnameSplit = this.practitionerNameSurname.split (" ");
    console.log(nameSurnameSplit[0], nameSurnameSplit[1]);
    this.httpPractitioner.getPractitionerByFullNameAndOrg(nameSurnameSplit[0], nameSurnameSplit[1], this.examination.organization.id).subscribe({
      next: practitioner => {
        this.checkIfAdded(practitioner)
      },
      error: error => this.toastService.showToast('Practitioner with that full name doesn\'t exist in selected organization!', {header:'Finding practitioner', className: 'bg-danger text-light'})
    }

    );
  }

  checkIfAdded(practitioner: Practitioner){ //checks if practitioner is already in list
    for(let i = 0; i < this.examinationPractitioners!.length; i++){
      if(this.examinationPractitioners![i].practitioner.id == practitioner.id){
        this.toastService.showToast('Practitioner ' + practitioner.name + ' ' + practitioner.surname + ' is already added!',
        {header:'Finding practitioner', className: 'bg-danger text-light'});
        return;
      }
    }
   this.assignPractitioner(practitioner);
  }

  assignPractitioner(practitioner: Practitioner){
    let examPract = {} as ExaminationPractitioner;
    examPract.practitioner = practitioner;
    this.toastService.showToast('Practitioner found successfully!', {header:'Finding practitioner', className: 'bg-success text-light'});
    this.examinationPractitioners?.push(examPract);
    this.practitionersListDisplay += this.examinationPractitioners?.[this.examinationPractitioners.length - 1].practitioner.name + ' ' +
    this.examinationPractitioners?.[this.examinationPractitioners.length - 1].practitioner.surname + '\n' ;
    // console.log(this.practitionersListDisplay);
  }

  deleteFromList(){
    var nameSurnameSplit = this.practitionerNameSurname.split (" ");
    console.log(nameSurnameSplit[0], nameSurnameSplit[1]);
    for(let i = 0; i < this.examinationPractitioners!.length; i++){
      if(this.examinationPractitioners![i].practitioner.name === nameSurnameSplit[0] &&
        this.examinationPractitioners![i].practitioner.surname === nameSurnameSplit[1]){
        this.examinationPractitioners?.splice(i, 1);
        this.reloadPractitionerListDisplay();
        return;
      }
    }
    this.toastService.showToast('Practitioner ' + nameSurnameSplit[0] + ' ' + nameSurnameSplit[1] + ' is not in list!',
        {header:'Deleting practitioner', className: 'bg-danger text-light'})
  }

  reloadPractitionerListDisplay(){ //used so that removed practitioner is no longer displayed
    this.practitionersListDisplay = '';
    for(let i = 0; i < this.examinationPractitioners!.length; i++){
      this.practitionersListDisplay += this.examinationPractitioners?.[i].practitioner.name + ' ' +
      this.examinationPractitioners?.[i].practitioner.surname + ' - ' +
      this.examinationPractitioners?.[i].practitioner.qualification + '\n';
    }
  }

  editExamination(){
    console.log('SE: ', this.examination);
    if(this.examinationPractitioners?.length == 0){
      this.toastService.showToast('Practitioners are required!', {header:'Updating examination', className: 'bg-danger text-light'});
      return;
    }
    if(this.examination.organization.name == null){
      this.toastService.showToast('Organization is required!', {header:'Updating examination', className: 'bg-danger text-light'});
      return;
    }
    if(this.examination.serviceType.id == null){
      this.toastService.showToast('Service type is required!', {header:'Updating examination', className: 'bg-danger text-light'});
      return;
    }
    if(this.examination.startDate != null && this.examination.endDate != null
      && this.examination.endDate < this.examination.startDate){
        this.toastService.showToast('End date can\'t be before start date!', {header:'Updating examination', className: 'bg-danger text-light'});
      return;
    }
     this.httpExamination.updateExamination(this.examination).subscribe({
      next: res => {
        this.checkIfExaminationIsUpdated(res)
      },
      error: error => this.toastService.showToast('Examination not updated: Examination with that identifier already exists!', {header:'Saving examination', className: 'bg-danger text-light'})
    }
    );
  }

  checkIfExaminationIsUpdated(res: Examination){
    console.log("UPD: ", res);
    if(res.hasOwnProperty('id')){
      this.updateExaminationPractitioners();
    } else{
      this.toastService.showToast('Examination not updated: ' + this.getMessage(res), {header:'Updating examination', className: 'bg-danger text-light'});
    }
  }

  getMessage(res : Examination){
    if(res.hasOwnProperty('identifier')){
      return res.identifier;
    }
    return res.status;
  }

  updateExaminationPractitioners(){
    this.deleteExaminationPractitioners(); //in case user has deleted some practitioners from list
    this.saveExaminationPractitioners();
  }

  async deleteExaminationPractitioners(){
    let res = await firstValueFrom(this.httpExamPract.delete(this.examination.id));
    console.log(res);
  }

  saveExaminationPractitioners(){
    let examPract = {} as ExaminationPractitioner;
    examPract.examination = this.examination;
    for(let i = 0; i < this.examinationPractitioners!.length; i++){
      examPract.practitioner = this.examinationPractitioners![i].practitioner;
      this.httpExamPract.saveExaminationPractitioner(examPract).subscribe(res => console.log(res));
    }
    this.toastService.showToast('Examination updated successfully!', {header:'Updating examination', className: 'bg-success text-light'});
    this.router.navigate(['/examination']);
  }

}
