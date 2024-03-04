import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Patient } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpPatientService {

  constructor(private httpClient: HttpClient) { }

  savePatient(patient: Patient){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<Patient>(`${environment.serverUrl}/patients`, patient, { headers: headers }).pipe(catchError(this.errorHandler));
  }

  getByPage(page: number, size: number, sortBy: string, sortOrder: string, name: string, surname: string, organization: string, practName: string, practSurname: string, unassigned: boolean) {
    return this.httpClient.get<any>(`${environment.serverUrl}/patients/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&name=${name}&surname=${surname}&organization=${organization}&practName=${practName}&practSurname=${practSurname}&unassigned=${unassigned}`);
  }

  deletePatient(id : number){
    return this.httpClient.delete(`${environment.serverUrl}/patients/${id}`,{responseType:'text'});
  }

  getPatient(id: number) : Observable<Patient>{
    return this.httpClient.get<Patient>(`${environment.serverUrl}/patients/${id}`);
  }

  updatePatient(patient: Patient){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<Patient>(`${environment.serverUrl}/patients/${patient.id}`, patient, { headers: headers }).pipe(catchError(this.errorHandler));
  }

  getPatientByFullName(name: string, surname: string) : Observable<Patient>{
    return this.httpClient.get<Patient>(`${environment.serverUrl}/patients/find/${name}&${surname}`).pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }

}
