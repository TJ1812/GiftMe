import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { Item } from '../model/item';

import { ItemService } from '../services/item.service';

@Component({
    selector: 'all-items-component',
    templateUrl: './all-items.component.html',
    styleUrls: ['./all-items.component.css']
})

export class AllItemsComponent implements OnInit{

    items: Item[];

    constructor(private itemService: ItemService,
    private location: Location){

    }

    ngOnInit(){
        this.getItems();
    }

    getItems(){
        this.itemService.getItems().subscribe(items => this.items = items);
    }    

    goBack() {
        this.location.back();
    }


}