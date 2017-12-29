import { Component, OnInit, Output, EventEmitter } from '@angular/core';


import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { of } from 'rxjs/observable/of';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Item } from '../model/item';
import { ItemService } from '../services/item.service';

@Component({
  selector: 'item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.css']
})

export class ItemSearchComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Item[]>();

  items: Observable<Item[]>;
  private searchTerms = new Subject<string>();

  shared_items: Item[];
  constructor(private itemService: ItemService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.shared_items = new Array<Item>();
    this.items = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.itemService.searchItems(term)),
    );
  }

  add(item: Item): void {
    if (!this.shared_items.includes(item)) {
      this.shared_items.push(item);
      this.messageEvent.emit(this.shared_items);
    }
  }

  delete(item: Item): void {
    this.shared_items = this.shared_items.filter(h => h !== item);
    this.messageEvent.emit(this.shared_items);
  }

  sendMessage() {
    this.messageEvent.emit(this.shared_items);
  }

}