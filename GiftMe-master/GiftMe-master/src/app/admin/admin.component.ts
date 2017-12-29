import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Profile } from '../model/profile';
import { UserIdService } from '../services/userId.service';


@Component({
    selector: 'admin-component',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})

export class AdminComponent implements OnInit {
    profile: Profile;

    constructor(private router: Router,
        private location: Location,
        private userIdService: UserIdService, ) {

    }

    ngOnInit() {
        this.profile = new Profile();
        this.profile["userId"] = this.userIdService.getUserId();

        if (this.profile.userId != "admin") {
            this.router.navigate(['/unauth']);
        }


    }

}