import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppConfig } from '../app-config';

@Injectable({
  providedIn: 'root'
})
export class RunnerProfileService {

  constructor(private httpClient:HttpClient) { }

  profileId:any=0;

  getProfileId(){
    return this.profileId;
  }

  setProfileId(profileId:any){
    this.profileId=profileId;
  }

  checkLoginDetails(loginDetails:any){
    return this.httpClient.post<any>(AppConfig.checkLoginDetails,loginDetails);
  }

  addUserProfile(indidualEvent:any){
    return this.httpClient.post<any>(AppConfig.addUserProfile,indidualEvent);

  }

  getAllMarathonData(profileId:any){
    return this.httpClient.get<any>(AppConfig.getAllMarathonData+"/"+profileId);
  }

  deleteRunnerData(registerId:any){
    return this.httpClient.delete<any>(AppConfig.deleteRunnerData,registerId);
  }

  deleteMarathonData(registerId:any){
    return this.httpClient.delete<any>(AppConfig.deleteMarathonData+"/"+registerId);
  }

  getAllRunnerData(){
    return this.httpClient.get<any>(AppConfig.getAllRunnerData);
  }

  getTopperData(){
    return this.httpClient.get<any>(AppConfig.getTopperData);
  }

  addRunnerProfile(indidualEvent:any){
    return this.httpClient.post<any>(AppConfig.addRunnerProfile,indidualEvent);
  }

  addMarathonDetail(indidualEvent:any){
    return this.httpClient.post<any>(AppConfig.addEventName,indidualEvent);
  }

  addWholeYearData(addedDetail:any){
    return this.httpClient.post<any>(AppConfig.addWholeYearData,addedDetail);
  }

  downloadPdf(columnList:any){
    var downloadReportUrl=AppConfig.downloadPdf;
    downloadReportUrl += '/' + columnList;
    var a = document.createElement('a');
    a.setAttribute ('style','display:none');
    a.href=downloadReportUrl;
    a.download = 'TirupurRunners_Report.pdf';
    a.target='_blank';
    a.click();
    window.URL.revokeObjectURL(downloadReportUrl);
    a.remove();
  }

  downloadEventPdf(marathonId:any){
    var downloadReportUrl=AppConfig.downloadEventPdf;
    downloadReportUrl += '/' + marathonId;
    var a = document.createElement('a');
    a.setAttribute ('style','display:none');
    a.href=downloadReportUrl;
    a.download = 'Event_Report.pdf';
    a.target='_blank';
    a.click();
    window.URL.revokeObjectURL(downloadReportUrl);
    a.remove();
  }

  getAllFutureMarathonData(){
    return this.httpClient.get<any>(AppConfig.getAllFutureMarathonData);
  }

  getAllAcesToken(){
    return this.httpClient.get<any>(AppConfig.getAllAcesToken);
  }

  replaceByMonth(topperdetails:any){
    return this.httpClient.post<any>(AppConfig.replaceBymonth,topperdetails);
  }

}
