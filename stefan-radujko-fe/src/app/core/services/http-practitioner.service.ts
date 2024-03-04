import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Practitioner } from 'src/app/models';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpPractitionerService {

  constructor(private httpClient: HttpClient) { }

  getByPage(page: number, size: number, sortBy: string, sortOrder: string, name: string, surname: string, qualification: string, organization: string, unassigned: boolean) {
    return this.httpClient.get<any>(`${environment.serverUrl}/practitioners/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&name=${name}&surname=${surname}&qualification=${qualification}&organization=${organization}&unassigned=${unassigned}`);
  }

  getPractitioner(id: number) : Observable<Practitioner>{
    return this.httpClient.get<Practitioner>(`${environment.serverUrl}/practitioners/${id}`);
  }

  updatePractitioner(practitioner: Practitioner){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<Practitioner>(`${environment.serverUrl}/practitioners/${practitioner.id}`, practitioner, { headers: headers });
  }

  deletePractitioner(id : number){
    return this.httpClient.delete(`${environment.serverUrl}/practitioners/${id}`,{responseType:'text'});
  }

  savePractitioner(practitioner: Practitioner){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<Practitioner>(`${environment.serverUrl}/practitioners`, practitioner, { headers: headers });
  }

  getPractitionerByFullNameAndOrg(name: string, surname: string, id : number) : Observable<Practitioner>{
    return this.httpClient.get<Practitioner>(`${environment.serverUrl}/practitioners/find/${name}&${surname}&${id}`).pipe(catchError(this.errorHandler));
  }

  getPractCountForOrg(id : number){
    return this.httpClient.get<any>(`${environment.serverUrl}/practitioners/count/${id}`);
  }

  getPractForOrg(id: number): Observable<Practitioner[]>{
    return  this.httpClient.get<Practitioner[]>(`${environment.serverUrl}/practitioners/findbyorg/${id}`);
  }


  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }
}
