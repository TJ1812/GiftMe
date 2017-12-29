import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/Rx';

import { Login } from '../model/login';
import { SignUp } from '../model/signup';

@Injectable()
export class LoginService {

    private baseURL = 'https://169.254.165.44:8443/jsp-servlet-mvc-restclient';
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {
    }

    loginService(loginReq: Login): Promise<Login> {
        const url = 'SessionControllerServlet';
        
        const loginApiURL = `${this.baseURL}/${url}`;
        const body = JSON.stringify(loginReq);
        return this.http
        .post(loginApiURL, body, {headers: this.headers})
        .toPromise()
        .then((res: Response) => res.json() as Login)
        .catch();
    }

    signupService(signupReq: SignUp): Promise<SignUp> {
        const url = 'SignupServlet';
       
        const signupApiURL = `${this.baseURL}/${url}`;
        const body = JSON.stringify(signupReq);

        return this.http
        .post(signupApiURL, body, {headers: this.headers})
        .toPromise()
        .then((res: Response) => res.json() as SignUp)
        .catch();
    }

}
