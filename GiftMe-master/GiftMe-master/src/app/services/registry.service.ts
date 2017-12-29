import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Registry } from '../model/registry';
import { UserId } from '../model/userId';
import { Item } from '../model/item';
import { SelfAssign } from '../model/selfassign';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class RegistryService {
    private baseUrl = 'https://169.254.165.44:8443/jsp-servlet-mvc-restclient';

    constructor(private http: HttpClient) { }

    createRegistry(regis: Registry): Observable<Registry> {

        const registryServlet = 'CreateRegistryServlet';
        const url = `${this.baseUrl}/${registryServlet}`;

        return this.http.post<Registry>(url, regis).pipe(
            tap(_ => console.log(`fetched registry id=${regis.userId}`)),
            catchError(this.handleError<Registry>(`getRegi id=${regis.userId}`))
        );


    }

    addUsersToRegistry(regis: Registry): Observable<Registry> {

        const addUsersServlet = 'AddUserToSharedRegistryServlet';
        const url = `${this.baseUrl}/${addUsersServlet}`;

        return this.http.post<Registry>(url, regis).pipe(
            tap(_ => console.log(`Item added succesfully.`)),
            catchError(this.handleError<Registry>(`Error in adding items.`))
        );

    }

    addItemsToRegistry(regis: Registry): Observable<Registry> {

        const addUsersServlet = 'AddItemRegistryServlet';
        const url = `${this.baseUrl}/${addUsersServlet}`;

        return this.http.post<Registry>(url, regis).pipe(
            tap(_ => console.log(`Item added in registry`)),
            catchError(this.handleError<Registry>(`Error in adding item to registry`))
        );

    }

    getUserRegisties(userId: UserId): Observable<Registry[]> {

        const getUserServlet = 'RetrieveUserRegistryServlet';
        const url = `${this.baseUrl}/${getUserServlet}`;

        return this.http.post<Registry[]>(url, userId).pipe(
            tap(_ => console.log(`successfull sending userId.`)),
            catchError(this.handleError<Registry[]>(`Error in sending userId`))
        );

    }

    getUserRegistiesItems(regiId: string): Observable<SelfAssign[]> {

        const getUserServlet = 'RetrieveItemsInRegistryServlet';
        const url = `${this.baseUrl}/${getUserServlet}`;

        return this.http.post<SelfAssign[]>(url, regiId).pipe(
            tap(_ => console.log(`successfull sending userId.`)),
            catchError(this.handleError<SelfAssign[]>(`Error in sending userId`))
        );

    }

    getSharedWithUserRegisties(userId: UserId): Observable<Registry[]> {

        const getUserServlet = 'RetrieveRegistriesSharedServlet';
        const url = `${this.baseUrl}/${getUserServlet}`;

        return this.http.post<Registry[]>(url, userId).pipe(
            tap(_ => console.log(`successfull sending shared userId.`)),
            catchError(this.handleError<Registry[]>(`Error in sending shared userId`))
        );

    }

    setUserAssign(special: SelfAssign): Observable<SelfAssign> {

        const getUserServlet = 'AssignEmailToRegistryItemServlet';
        const url = `${this.baseUrl}/${getUserServlet}`;

        return this.http.post<SelfAssign>(url, special).pipe(
            tap(_ => console.log(`successfull sending shared userId.`)),
            catchError(this.handleError<SelfAssign>(`Error in sending shared userId`))
        );
    }

    /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error);
            console.log(`${operation} failed: ${error.message}`);
            return of(result as T);
        };
    }

}   