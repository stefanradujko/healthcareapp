import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HttpUserService } from 'src/app/core/services/http-user.service';


import { ToastService } from 'src/app/core/services/toast.service';
import { UserLoginService } from 'src/app/core/services/user-login.service';
import { Practitioner } from 'src/app/models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm?: FormGroup;
  subscriptions: Subscription = new Subscription();
  constructor(
    private fb: FormBuilder,
    private httpUser: HttpUserService,
    private router: Router,
    private userLogin: UserLoginService,
    private toastService: ToastService,
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  ngOnDestroy(): void {
      console.log('Unsubscribe subscriptions');
      this.subscriptions.unsubscribe();
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  @ViewChild('succesLoginMessage') messageTempl?: TemplateRef<any>;
    login() {
    this.subscriptions.add(
      this.httpUser.login(this.loginForm?.value).subscribe({
        next: practitioner => {
          this.goHome(practitioner)
        },
        error: error => this.toastService.showToast('Unsuccessfull login!', {header:'Login', className: 'bg-danger text-light'})
      }
      )
      );
  }

  goHome(practitioner: Practitioner){
    console.log('userDetails:', practitioner);
          this.userLogin.setLoginCredentials(practitioner);
          this.toastService.showToast('Welcome ' + practitioner.name + '!', {header:'Login', className: 'bg-success text-light'})
          this.router.navigate(['/home']);
  }
}


/*
(userDetails: Practitioner) => {
          console.log('userDetails:', userDetails);
          this.userLogin.setLoginCredentials(userDetails);
          this.toastService.showToast( this.messageTempl? this.messageTempl : 'Successfull login' ,{ header: 'Login'})
          this.router.navigate(['/home']);
        },
*/
