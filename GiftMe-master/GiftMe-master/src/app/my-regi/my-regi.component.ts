import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { RegistryService } from '../services/registry.service';
import { UserIdService } from '../services/userId.service';
import { UserId } from '../model/userId';
import { Registry } from '../model/registry';
import { Item } from '../model/item';
import { SelfAssign } from '../model/selfassign';


@Component({
    selector: 'myregi-component',
    templateUrl: './my-regi.component.html',
    styleUrls: ['./my-regi.component.css']
})


export class MyRegistryComponent implements OnInit {

    registry_names: Registry[];
    item_names: SelfAssign[];
    self_assigned: string;
    userId: UserId;

    constructor(
        private registryService: RegistryService,
        private userIdService: UserIdService,
        private router: Router,
        private location: Location,
    ) { }

    ngOnInit() {
        this.registry_names = new Array<Registry>();
        this.item_names = new Array<SelfAssign>();
        this.userId = new UserId();

        this.userId["userId"] = this.userIdService.getUserId(); 
   
        if (this.userId.userId == undefined) {
            this.router.navigate(['/unauth']);
        }


        this.getAllRegistriesOfUser(this.userId);
    }



    getAllRegistriesOfUser(userID: UserId) {
        this.self_assigned = null;

        this.registryService.getUserRegisties(userID).subscribe(res => 
            this.registry_names = res
        );

    }


    getItemsForRegistry(registry: string) {
        this.self_assigned = null;

        this.registryService.getUserRegistiesItems(registry).subscribe(res => 
            this.item_names = res
        );
    }

    goBack() {
        this.location.back();
    }



}
