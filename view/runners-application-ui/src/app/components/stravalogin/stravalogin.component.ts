import { Component,OnInit } from '@angular/core';
import { OAuthService } from 'src/app/services/oauth.service';
import { StravaService } from 'src/app/services/strava.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NativeDateAdapter } from '@angular/material/core';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { wholeYearData } from '../marathon-profile';

@Component({
  selector: 'app-stravalogin',
  templateUrl: './stravalogin.component.html',
  styleUrls: ['./stravalogin.component.scss']
})
export class StravaloginComponent implements OnInit {

  activities: any[] = [];
  accessToken: string = ''; // Load this from storage or input
  totalDistance: number = 0;
  totalTime: number = 0;
  totalrun:number = 0;
  distanceYear: String ='';
  distanceTime: String ='';
  userName:String='';
  startDate:any;
  endDate:any;
  athleteId:any;
  decimalKilometer:any;
  wholeyearData: wholeYearData = new wholeYearData;
  expiresAt:any;
  refreshToken:any;
  //date='2024-12-31T00:00:00Z'

  runnersdisplayedColumns: string[] = ['userName', 'totalRuns', 'totalDistance'];

  constructor(private oauthService: OAuthService,
    private stravaService: StravaService,
    private route: ActivatedRoute,
    private router: Router,
    private dateAdapter: NativeDateAdapter,
    private runnerProfileService:RunnerProfileService,) {}

  ngOnInit(): void {
   this.route.queryParams.subscribe(params => {
      const code = params['code'];
      if (code) {
        this.oauthService.exchangeCodeForToken(code).subscribe(
          response => {
            console.log('Access Token:', response.access_token);
            // Save the token in localStorage or handle it as needed
            localStorage.setItem('access_token', response.access_token);
            this.userName=response['athlete']['firstname'];
            this.athleteId=response['athlete']['id'];
            this.expiresAt=response.expires_at;
            this.refreshToken=response.refresh_token;
            this.accessToken = localStorage.getItem('access_token') || ''; // Replace with your access token management
            if (this.accessToken) {
                this.fetchActivities();
            }
          },
         error => {
            console.error('Error exchanging code for token', error);
            this.router.navigate(['/']);
          }
        );
      }
    });
  }

  login() {
    window.location.href = this.oauthService.getAuthorizationUrl();
  }

  getMarathonDetail(){
    this.accessToken = localStorage.getItem('access_token') || ''; // Replace with your access token management
    if (this.accessToken) {
        this.fetchActivities();
    }
  }

  fetchActivities(page: number = 1, perPage: number = 200): void {
    this.stravaService.getUserActivities(this.accessToken, page, perPage).subscribe(
      response => {
        this.activities = response;
        if (this.activities) {
          
          this.activities = this.activities.filter(activity => {
            const activityDate = new Date(activity['start_date']);
            return (activityDate >= new Date('2024-01-01T00:00:00Z')) &&
            ( activityDate <= new Date('2024-12-31T00:00:00Z'));
          });
          this.activities.forEach(run => {
            if(run['type']==="Run"){
              this.totalrun+=1;
              this.totalDistance+= Number.parseFloat(run['distance']);
              this.totalTime+= Number.parseFloat(run['moving_time']);
             // let date=this.dateAdapter.format(run['start_date'],'MM/dd/yyyy');

             
              console.log(run['start_date']);
            }
          });

          var km = this.totalDistance / 1000;
          this.decimalKilometer=km.toFixed(2);
          this.distanceYear=km.toFixed(2) + " km";
        }
        console.log('Activities:', this.activities);
        console.log('Athlete Id:', this.athleteId);
        this.saveValue();
      },
      error => {
        console.error('Error fetching activities:', error);
      }
    );
  }

  saveValue(){
    this.wholeyearData.userName=this.userName;
    this.wholeyearData.athleteId=this.athleteId;
    this.wholeyearData.totalKilometer=this.distanceYear;
    this.wholeyearData.decimalKilometer=this.decimalKilometer;
    this.wholeyearData.code=localStorage.getItem('access_token');
    this.wholeyearData.refreshToken=this.refreshToken;
    this.wholeyearData.expiresAt=this.expiresAt;
    this.runnerProfileService.addWholeYearData(this.wholeyearData).subscribe(
      response => {
        console.log('Success');
      });
  }
}

