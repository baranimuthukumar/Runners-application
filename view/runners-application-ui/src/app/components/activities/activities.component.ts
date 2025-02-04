import { Component, OnInit } from '@angular/core';
import { StravaService } from 'src/app/services/strava.service';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.scss']
})
export class ActivitiesComponent implements OnInit {
  activities: any[] = [];
  accessToken: string = ''; // Load this from storage or input
  totalDistance: number = 0;
  totalTime: number = 0;
  totalrun:number = 0;
  distanceYear: String ='';
  distanceTime: String ='';

  constructor(private stravaService: StravaService) {}

  ngOnInit(): void {
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
          this.activities.forEach(run => {
            if(run['type']==="Run"){
              this.totalrun+=1;
              this.totalDistance+= Number.parseFloat(run['distance']);
              this.totalTime+= Number.parseFloat(run['moving_time']);
            }
          });

          var km = this.totalDistance / 1000;
          this.distanceYear=km.toFixed(2) + " km";
        }
        console.log('Activities:', this.activities);
      },
      error => {
        console.error('Error fetching activities:', error);
      }
    );
  }
}