import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Profile } from '../model/profile';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProfileService {

    private baseURL = 'https://169.254.165.44:8443/jsp-servlet-mvc-restclient';

    constructor(private http: HttpClient) { }

    /* GET users containing search term */
    searchUsers(term: string): Observable<Profile[]> {
        if (!term.trim()) {
            return of([]);
        }

        const userServlet = 'UserSearchServlet';
        const url = `${this.baseURL}/${userServlet}`;

        return this.http.post<Profile[]>(url, term).pipe(
            tap(_ => console.log(`found users matching "${term}"`)),
            catchError(this.handleError<Profile[]>('searchUsers', []))
        );
    }

    getProfile(prof: Profile): Observable<Profile> {

        const url = 'UserProfileServlet';
        const getProfileApiURL = `${this.baseURL}/${url}`;

        return this.http.post<Profile>(getProfileApiURL, prof).pipe(
            tap(_ => console.log(`fetched profile for id=${prof.userId}`)),
            catchError(this.handleError<Profile>(`getProfile id=${prof.userId}`))
        );

    }

    updateProfile(prof: Profile): Observable<Profile> {

        const url = 'UpdateUserProfileServlet';
        const getProfileApiURL = `${this.baseURL}/${url}`;

        return this.http.post<Profile>(getProfileApiURL, prof).pipe(
            tap(_ => console.log(`updated profile for id=${prof.userId}`)),
            catchError(this.handleError<Profile>(`updateProfile id=${prof.userId}`))
        );

    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error);
            console.log(`${operation} failed: ${error.message}`);
            return of(result as T);
        };
    }


}