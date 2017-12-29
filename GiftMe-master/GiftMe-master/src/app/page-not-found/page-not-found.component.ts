import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'pagenotfound-component',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})


export class PageNotFoundComponent {
  title = 'page not found text';

  constructor(private location: Location) {
    
  }

  goBack(): void {
    this.location.back();
  }

}
