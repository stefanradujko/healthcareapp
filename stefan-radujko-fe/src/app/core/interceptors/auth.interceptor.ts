import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserLoginService } from '../services/user-login.service';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private userLogin: UserLoginService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('intercept');
    const token = this.userLogin.token;
    if (token) {
      request = request.clone (
        {headers: request.headers.set('Authorization', token)}
      );

    }
    return next.handle(request);
  }
}
