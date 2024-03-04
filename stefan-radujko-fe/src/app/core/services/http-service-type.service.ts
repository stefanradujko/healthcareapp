import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ServiceType } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpServiceTypeService {

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<ServiceType[]> {
    return this.httpClient.get<ServiceType[]>(`${environment.serverUrl}/services/type`);
  }

  saveAll(serviceTypes: ServiceType[]){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<ServiceType>(`${environment.serverUrl}/services/type`, serviceTypes, { headers: headers }).pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }
}
