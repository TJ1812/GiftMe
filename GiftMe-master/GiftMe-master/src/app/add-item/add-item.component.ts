import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';
import { ItemService } from '../services/item.service';

import { Item } from '../model/item';

@Component({
    selector: 'add-item',
    templateUrl: './add-item.component.html',
    styleUrls: ['./add-item.component.css']
})

export class AddItemComponent implements OnInit {
    @Input() item: Item;
    message: string;

    constructor(private itemService: ItemService,
        private location: Location) {

    }

    ngOnInit() {
        this.item = new Item();
    }

    addItem() {
        this.itemService.addItem(this.item).subscribe(item => {
            this.item = item
            this.message = "Item Succesfully Added!";
            this.item["name"] = "";
            this.item["description"] = "";
            this.item["photo"] = "";
            this.item["price"] = "";
            this.item["quantity"] = "";
            this.item["category"] = "";

        }
        );
    }

    goBack() {
        this.location.back();
    }

}