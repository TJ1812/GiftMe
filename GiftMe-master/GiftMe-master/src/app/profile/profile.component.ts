import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { Profile } from '../model/profile';

import { ProfileService } from '../services/profile.service';
import { UserIdService } from '../services/userId.service';

@Component({
    selector: 'profile-component',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
    profile: Profile;

    constructor(
        private profileService: ProfileService,
        private userIdService: UserIdService,
        private router: Router,
        private location: Location) { };

    ngOnInit() {
        this.profile = new Profile();
        this.profile["userId"] = this.userIdService.getUserId();
        // this.profile["token"] = this.userIdService.getToken();
        //this.profile["token"] = "50";

        if (this.profile.userId == undefined) {
            this.router.navigate(['/unauth']);
        }

        this.getUserProfile(this.profile);
    }

    getUserProfile(prof: Profile) {

        this.profileService.getProfile(prof).subscribe(res => {
            this.profile = res;
        });

    }

    updateUser() {
        this.profileService.updateProfile(this.profile).subscribe(res => {
            this.profile = res;
        });

    }

    goBack() {
        this.location.back();
    }

}
