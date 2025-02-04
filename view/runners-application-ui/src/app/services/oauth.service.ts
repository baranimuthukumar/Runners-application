import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OAuthService {
  private clientId = '125589';
  private clientSecret = '8a343f2463827c3ca561b538627eaec1d37a723e';
  private redirectUri = 'http://localhost:4200/strava-login';

  constructor(private http: HttpClient) {}

  getAuthorizationUrl(): string {
    const scope = 'activity:read_all';
    return `https://www.strava.com/oauth/authorize?client_id=${this.clientId}&response_type=code&redirect_uri=${this.redirectUri}&approval_prompt=force&scope=${scope}`;
  }

  exchangeCodeForToken(code: string): Observable<any> {
    const url = 'https://www.strava.com/oauth/token';
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    const params = new HttpParams()
      .set('client_id', this.clientId)
      .set('client_secret', this.clientSecret)
      .set('code', code)
      .set('grant_type', 'authorization_code');

    return this.http.post(url, params, { headers });
  }

  generateAccessTokenUsingRefresh(code: string): Observable<any> {
    const url = 'https://www.strava.com/oauth/token';
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    const params = new HttpParams()
      .set('client_id', this.clientId)
      .set('client_secret', this.clientSecret)
      .set('grant_type', 'refresh_token')
      .set('refresh_token', code);

    return this.http.post(url, params, { headers });
  }
}