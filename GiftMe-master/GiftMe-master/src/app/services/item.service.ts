import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Item } from '../model/item';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ItemService {

  private baseUrl = 'https://169.254.165.44:8443/jsp-servlet-mvc-restclient'; 

  constructor(private http: HttpClient) { }

  /** GET items from the server */
  getItems(): Observable<Item[]> {

    const itemServlet = 'ItemSearchServlet';
    const url = `${this.baseUrl}/${itemServlet}`;

    return this.http.get<Item[]>(url)
      .pipe(
        tap(items => console.log(`fetched items`)),
        catchError(this.handleError('getItems', []))
      );
  }

  /** GET item by id. Return `undefined` when id not found */
  getItemNo404<Data>(id: number): Observable<Item> {
    const itemServlet = 'ItemSearchServlet';
    const url = `${this.baseUrl}/${itemServlet}/?id=${id}`;

    return this.http.get<Item[]>(url)
      .pipe(
        map(items => items[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? `fetched` : `did not find`;
          console.log(`${outcome} item id=${id}`);
        }),
        catchError(this.handleError<Item>(`getItem id=${id}`))
      );
  }

  /** GET item by id. Will 404 if id not found */
  getItem(id: string): Observable<Item> {
    
    const itemServlet = 'ItemSearchServlet';
    const url = `${this.baseUrl}/${itemServlet}`;
    
    return this.http.post<Item>(url, id).pipe(
      tap(_ => console.log(`fetched item id=${id}`)),
      catchError(this.handleError<Item>(`getItem id=${id}`))
    );
  }

  /* GET items containing search term */
  searchItems(term: string): Observable<Item[]> {
    if (!term.trim()) {
      return of([]);
    }

    const itemServlet = 'RetrieveItemServlet';
    const url = `${this.baseUrl}/${itemServlet}`;

    return this.http.post<Item[]>(url, term).pipe(
      tap(_ => console.log(`found items matching "${term}"`)),
      catchError(this.handleError<Item[]>('searchItems', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new item to the server */
  addItem (item: Item): Observable<Item> {
    const itemServlet = 'AddItemServlet';
    const url = `${this.baseUrl}/${itemServlet}`;

    const body = JSON.stringify(item);

    return this.http.post<Item>(url, body, httpOptions).pipe(
      tap((item: Item) => console.log(`added item`)),
      catchError(this.handleError<Item>('addItem'))
    );
  }

  /** DELETE: delete the item from the server */
  deleteItem (id: string): Observable<Item> {
    const itemServlet = 'DeleteItemServlet';
    const url = `${this.baseUrl}/${itemServlet}`;

    return this.http.post<Item>(url, id).pipe(
      tap(_ => console.log(`deleted item id=${id}`)),
      catchError(this.handleError<Item>('deleteItem'))
    );
  }

  /** PUT: update the item on the server */
  updateItem (item: Item): Observable<any> {
    const itemServlet = 'ItemSearchServlet';
    const url = `${this.baseUrl}/${itemServlet}`;
    
    return this.http.put(url, item, httpOptions).pipe(
      tap(_ => console.log(`updated item id=${item.itemId}`)),
      catchError(this.handleError<any>('updateItem'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

}
