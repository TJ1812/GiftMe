import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Item } from '../model/item';

import { ItemService } from '../services/item.service';
import { Local } from 'protractor/built/driverProviders';
import { UserIdService } from '../services/userId.service';
import { UserId } from '../model/userId';

@Component({
    selector: 'item-detail-component',
    templateUrl: './item-detail.component.html',
    styleUrls: ['./item-detail.component.css']
})

export class ItemDetailComponent implements OnInit {
    userId: UserId;
    item: Item;
    show: boolean = false;

    constructor(private route: ActivatedRoute,
        private itemService: ItemService,
        private location: Location,
        private userIdService: UserIdService,) {

    }

    ngOnInit() {
        this.userId = new UserId();
        this.userId["userId"] = this.userIdService.getUserId();

        if (this.userId.userId == "admin") {
            this.show = true;
        }
        
        this.item = new Item();
        this.getItem();
    }

    getItem() {
        const id = this.route.snapshot.paramMap.get('id');
        this.itemService.getItem(id).subscribe(item => this.item = item);
    }

    delete() {
        const id = this.route.snapshot.paramMap.get('id');
        this.itemService.deleteItem(id).subscribe(res => this.goBack());
    }

    goBack() {
        this.location.back();
    }
}