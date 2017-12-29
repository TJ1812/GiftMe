import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Login } from '../model/login';
import { SignUp } from '../model/signup';

import { LoginService } from '../services/login-service';
import { UserIdService } from '../services/userId.service';

@Component({
  selector: 'login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit {
  login: Login;
  signup: SignUp;
  
  show: boolean = false;

  constructor(
    private loginService: LoginService,
    private userIdService: UserIdService,
    private router: Router
    ) {}


  changeShow(){
    this.show = !this.show;
  }

  ngOnInit() {
      this.login = new Login();
      this.signup = new SignUp();
      this.userIdService.setUserId(null);
      this.userIdService.setUserName(null);
  }


  loginUser() {

    if(this.login["email"]=="admin" && this.login["password"]=="admin"){
      this.userIdService.setUserId("admin");
      this.userIdService.setUserName("admin");
      this.router.navigate(['/admin']);
    }
    
    if ( (!this.login["email"] && !this.login["passowrd"]) || (!this.login["email"] && !this.login["answer"]) ) {
      this.login["message"] = "Please fill username and password";
      this.login["success"] = false;
      return;
    }



    this.loginService.loginService(this.login).then(res => {
        this.login = res;
        
        if (this.login["success"]) {
          this.userIdService.setUserId(this.login["userId"]);
          this.userIdService.setUserName(this.login["name"]);
          // this.userIdService.setToken(this.login["token"]);
          // console.log(this.userIdService.getToken());
          this.router.navigate(['/dashboard']);
        } 
    });

  }

  signUpUser(type: string ): void {

    if(type == "0"){
      if (!this.signup["email"] || !this.signup["password"] || !this.signup["name"] || 
          !this.signup["address"] || !this.signup["phone"] || !this.signup["answer"]) {
            this.signup["message"] = "Please fill username and password";
            this.signup["success"] = false;
            return;
      }
    }

    this.signup["type"] = type;

    this.loginService.signupService(this.signup).then(res => {
      this.signup = res;

      if (this.signup["success"]) {
        this.signup["email"] = "";
        this.signup["name"] = "";
        this.signup["password"] = "";
        this.signup["address"] = "";
        this.signup["phone"] = "";
        this.signup["answer"] = "";
      } 
  
    });

  }

}
