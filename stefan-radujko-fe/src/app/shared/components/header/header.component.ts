import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { UserLoginService } from 'src/app/core/services/user-login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() languageChange = new EventEmitter<string>();
  userData: {name: string, surname: string} | null  = {name:'', surname:''};
  selectedLanguge = 'en';
  availableLangugaes = ['en', 'it'];


  constructor(private userLoginService: UserLoginService, private router: Router) { }

  ngOnInit(): void {
    this.userLoginService.userDataSubject.subscribe( userData => this.userData = userData)
  }

  logoutUser() {
    this.userLoginService.logoutUser();
    this.router.navigate(["/login"]);
  }

  selectLanguage(language: string) {
    console.log('langugae:', language);
    this.selectedLanguge = language;
    this.languageChange.emit(language)
  }
}
