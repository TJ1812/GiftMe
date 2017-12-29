import { Component, OnInit, Output, EventEmitter } from '@angular/core';


import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { of } from 'rxjs/observable/of';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Profile } from '../model/profile';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.css']
})

export class UserSearchComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<Profile[]>();

  profiles: Observable<Profile[]>;
  private searchTerms = new Subject<string>();

  shared_profs: Profile[];
  constructor(private profileService: ProfileService) { }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.shared_profs = new Array<Profile>();
    this.profiles = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.profileService.searchUsers(term)),
    );
  }

  add(prof: Profile): void {
    this.shared_profs.push(prof);
    this.messageEvent.emit(this.shared_profs);
  }

  delete(prof: Profile): void {
    this.shared_profs = this.shared_profs.filter(h => h !== prof);
    this.messageEvent.emit(this.shared_profs);
  }

  sendMessage() {
    this.messageEvent.emit(this.shared_profs);
  }
}