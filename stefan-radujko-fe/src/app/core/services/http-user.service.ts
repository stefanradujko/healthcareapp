import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Practitioner } from 'src/app/models';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HttpUserService {

  constructor(private httpClient: HttpClient) { }

  login({username, password}: {username: string, password: string}): Observable<Practitioner> {

    const params = new URLSearchParams();
    let pract = {} as Practitioner;
    pract.username = username;
    pract.password = password;
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.httpClient.post<Practitioner>(`${environment.serverUrl}/auth/login`, pract, { headers: headers }).pipe(catchError(this.errorHandler));
  }
  errorHandler(error: HttpErrorResponse){
    return throwError(() => error);
  }

}
