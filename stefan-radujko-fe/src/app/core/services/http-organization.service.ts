import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Organization } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpOrganizationService {

  constructor(private httpClient: HttpClient) { }

  saveOrganization(organization: Organization){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<Organization>(`${environment.serverUrl}/organizations`, organization, { headers: headers });
  }

  getOrganization(id: number) : Observable<Organization>{
    return this.httpClient.get<Organization>(`${environment.serverUrl}/organizations/${id}`);
  }

  getByPage(page: number, size: number, sortBy: string, sortOrder: string, name: string, orgType: string) {
    return this.httpClient.get<any>(`${environment.serverUrl}/organizations/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&name=${name}&orgType=${orgType}`);
  }

  getOrganizationByName(name: string) : Observable<Organization>{
    return this.httpClient.get<Organization>(`${environment.serverUrl}/organizations/find/${name}`).pipe(catchError(this.errorHandler));
  }

  updateOrganization(organization: Organization){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<Organization>(`${environment.serverUrl}/organizations/${organization.id}`, organization, { headers: headers });
  }

  deleteOrganization(id : number){
    return this.httpClient.delete(`${environment.serverUrl}/organizations/${id}`,{responseType:'text'}).pipe(catchError(this.errorHandler));
  }

  getAll(): Observable<Organization[]> {
    return this.httpClient.get<Organization[]>(`${environment.serverUrl}/organizations`);
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }
}
