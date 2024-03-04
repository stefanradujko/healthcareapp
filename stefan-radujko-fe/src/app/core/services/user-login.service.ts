import { Injectable } from '@angular/core';
import { BehaviorSubject} from 'rxjs';
import { Practitioner } from 'src/app/models';



type userDataType =  {name: string, surnname: string};

@Injectable({
  providedIn: 'root'
})
export class UserLoginService {

  userDataSubject: BehaviorSubject<{name: string, surname: string} | null > = new BehaviorSubject<{name: string, surname: string} | null >(null) ;

  constructor() {
    const userDataString = this.storage.getItem('userData');
    if (userDataString) {
      this.userDataSubject.next(JSON.parse(userDataString));
    }

  }

  get storage() {
    return localStorage;
  }

  setLoginCredentials(userDetails: Practitioner) {
    this.storage.setItem('token', 'Basic ' + btoa(`${userDetails.username}:${userDetails.password}`))
    const userData = { name: userDetails.name, surname: userDetails.surname};
    this.storage.setItem('userData', JSON.stringify(userData));
    this.userDataSubject.next(userData);
  }

  logoutUser() {
    this.storage.removeItem('token');
    this.storage.removeItem('userData');
    this.userDataSubject.next(null);
  }

  get token() {
    return this.storage.getItem('token');
  }

  get userData() {
    return this.userDataSubject.getValue();
  }

  get isUserLoggedIn() {
    return !!this.token;
  }


}
