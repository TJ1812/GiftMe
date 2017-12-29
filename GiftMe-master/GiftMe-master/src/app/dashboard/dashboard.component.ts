import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

import { UserIdService } from '../services/userId.service';
import { ProfileService } from '../services/profile.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'dashboard-component',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})


export class DashboardComponent implements OnInit{
  
  userId: string;
  userName: string;

  constructor(
    private userIdService: UserIdService,
    private profileService: ProfileService,
    private router: Router){}

  ngOnInit(){

    this.userId = this.userIdService.getUserId();
    this.userName = this.userIdService.getUserName();

    if(this.userId == undefined){
      this.router.navigate(['/unauth']);
    }

  }
   
}