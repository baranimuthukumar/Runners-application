import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  upload(file: File,profileId:any): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('profileId',profileId);
    //const req = new HttpRequest('POST', `${this.baseUrl}/uploadFile`, formData, {
    const req = new HttpRequest('POST', `/uploadFile`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  getFiles(profileId:any): Observable<HttpEvent<any>> {

    const formData: FormData = new FormData();
    formData.append('profileId',profileId);
    const req = new HttpRequest('POST', `/files`, formData, {
        reportProgress: true,
        responseType: 'json'
      });
      return this.http.request(req);
  }

  deletefile(profileId:any,fileId:any): Observable<HttpEvent<any>> {

    const formData: FormData = new FormData();
    formData.append('profileId',profileId);
    formData.append('fileId',fileId);
    const req = new HttpRequest('POST', `/deleteFile`, formData, {
        reportProgress: true,
        responseType: 'json'
      });
      return this.http.request(req);
  }
}