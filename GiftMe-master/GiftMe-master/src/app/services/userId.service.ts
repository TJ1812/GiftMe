import { Injectable } from '@angular/core';

@Injectable()
export class UserIdService{

    userId: string;
    userName: string;
    token:  string;

    setUserId(id: string){
        this.userId = id;
    }

    setUserName(name: string){
        this.userName = name;
    }

    // setToken(token: string){
    //     this.token = token;
    // }

    getUserId(): string{
        return this.userId;
    }

    getUserName(): string{
        return this.userName;
    }

    // getToken(): string{
    //     return this.token;
    // }

} 