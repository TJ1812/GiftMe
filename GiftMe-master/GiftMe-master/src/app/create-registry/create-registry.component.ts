import { Component, OnInit, Input } from '@angular/core';

import { UserId } from '../model/userId';
import { Registry } from '../model/registry';

import { Profile } from '../model/profile';
import { Item } from '../model/item';

import { RegistryService } from '../services/registry.service';
import { UserIdService } from '../services/userId.service';


@Component({
    selector: 'create-registry-component',
    templateUrl: './create-registry.component.html',
    styleUrls: ['./create-registry.component.css']
})

export class CreateRegistryComponent implements OnInit {


    registry: Registry;
    shared_users: Profile[];
    shared_items: Item[];

    constructor(
        private registryService: RegistryService,
        private userIdService: UserIdService) {
    }

    ngOnInit() {
        this.registry = new Registry();
        this.registry["showPublic"] = true;
    }

    createRegistry() {
        this.registry["userId"] = this.userIdService.getUserId();
        this.registryService.createRegistry(this.registry).subscribe(regi => {
            this.registry = regi;

            if (this.shared_items != undefined) {
                this.shared_items.forEach(element => {
                    this.registry["itemId"] = element["itemId"];
                    this.registryService.addItemsToRegistry(this.registry).subscribe();
                });
            }

            if (!this.registry["showPublic"]) {
                if (this.shared_users != undefined) {
                    this.shared_users.forEach(element => {
                        this.registry["sharedUserId"] = element["userId"];
                        this.registryService.addUsersToRegistry(this.registry).subscribe();
                    });
                }
            }
        });

    }

    receiveMessageFromUserSearch($event) {
        this.shared_users = $event;
    }

    receiveMessageFromItemSearch($event) {
        this.shared_items = $event;
    }

}