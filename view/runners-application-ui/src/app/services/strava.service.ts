import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StravaService {
  private activitiesUrl = 'https://www.strava.com/api/v3/athlete/activities';
  private athleteUrl = 'https://www.strava.com/api/v3/athlete';

  constructor(private http: HttpClient) {}

  getUserActivities(accessToken: string, page: number = 1, perPage: number = 200): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.get(this.activitiesUrl, {
      headers: headers,
      params: {
        page: page.toString(),
        per_page: perPage.toString()
      }
    });
  }

  getUserDetails(accessToken: string): Observable<any>{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.get(this.athleteUrl, {
      headers: headers
      });
  }
  
}