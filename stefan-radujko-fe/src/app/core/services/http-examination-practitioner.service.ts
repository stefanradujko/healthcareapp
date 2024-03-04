import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ExaminationPractitioner } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpExaminationPractitionerService {

  constructor(private httpClient: HttpClient) { }

  saveExaminationPractitioner(examPract: ExaminationPractitioner){
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post<ExaminationPractitioner>(`${environment.serverUrl}/examinations/practitioners`, examPract, { headers: headers }).pipe(catchError(this.errorHandler));
  }

  getExamPractByExamId(id: number) : Observable<ExaminationPractitioner[]>{
    return this.httpClient.get<ExaminationPractitioner[]>(`${environment.serverUrl}/examinations/practitioners/find/${id}`);
  }

  delete(id : number){
    return this.httpClient.delete(`${environment.serverUrl}/examinations/practitioners/${id}`,{responseType:'text'});
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }
}
