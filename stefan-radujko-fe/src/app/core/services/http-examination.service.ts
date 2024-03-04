import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Examination } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpExaminationService {

  constructor(private httpClient: HttpClient) { }

  saveExamination(examination: Examination){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<Examination>(`${environment.serverUrl}/examinations`, examination, { headers: headers }).pipe(catchError(this.errorHandler));
  }

  getByPage(page: number, size: number, sortBy: string, sortOrder: string, organization: string, name: string, surname: string, serviceType: string, status: string, priority: string) {
    return this.httpClient.get<any>(`${environment.serverUrl}/examinations/filter?pageNo=${page-1}&pageSize=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&organization=${organization}&name=${name}&surname=${surname}&serviceType=${serviceType}&status=${status}&priority=${priority}`);
  }

  deleteExamination(id : number){
    return this.httpClient.delete(`${environment.serverUrl}/examinations/${id}`,{responseType:'text'});
  }

  getExamination(id: number) : Observable<Examination>{
    return this.httpClient.get<Examination>(`${environment.serverUrl}/examinations/${id}`);
  }

  updateExamination(examination: Examination){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.put<Examination>(`${environment.serverUrl}/examinations/${examination.id}`, examination, { headers: headers });
  }

  getExamCountForOrg(id : number){
    return this.httpClient.get<any>(`${environment.serverUrl}/examinations/count/${id}`);
  }

  getExamInProgressCountForOrg(id : number){
    return this.httpClient.get<any>(`${environment.serverUrl}/examinations/count-active/${id}`);
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }
}
