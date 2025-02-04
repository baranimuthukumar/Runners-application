import { Component, ViewChild } from '@angular/core';
import { RunnerDetailComponent } from '../runner-detail/runner-detail.component';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { MarathonEvent, TopperData, wholeYearData } from '../marathon-profile';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSelectChange } from '@angular/material/select';
import {MatSnackBar} from '@angular/material/snack-bar';
import { StravaService } from 'src/app/services/strava.service';
import { OAuthService } from 'src/app/services/oauth.service';

@Component({
  selector: 'app-runners-admin',
  templateUrl: './runners-admin.component.html',
  styleUrls: ['./runners-admin.component.scss']
})
export class RunnersAdminComponent {

  activities: any[] = [];
  accessToken: string = ''; // Load this from storage or input

  displayedColumns: string[] = ['serialNo', 'userName','dob','contactNumber',
                                'personalBest','tshirtSize','tshirtIssued','actionEdit','actionPDF','action'];
  
  runnersdisplayedColumns: string[] = ['rank', 'athlete', 'distance','activeDays'];

  selectedList = new FormControl();

  columnList:string[] = ['serialNo','userName', 'email','dob','contactNumber','nonActivemember','ageCategory',
                          'personalBest','tshirtSize','tshirtIssued'];
  dataSource!: MatTableDataSource<any>;

  topperdataSource!: MatTableDataSource<any>;

  @ViewChild('runnerTablePage') paginator!: MatPaginator;
  @ViewChild('runnerTable', { read: MatSort, static: true }) sort!: MatSort;
  @ViewChild('topperMarathon') topperMarathon!: MatPaginator;
  @ViewChild('topperTable', { read: MatSort, static: true }) toppersort!: MatSort;

  marathonEvent:MarathonEvent = new MarathonEvent();
  eventNames:MarathonEvent[]=[];
  monthDetails:string[]=['Whole-Year','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  eventName:any;
  marathonId:any;
  eventYear:any;
  showMsg:boolean= false;
  wholeyearData: wholeYearData[] = [];
  wholeyearDataEn:wholeYearData = new wholeYearData();
  refreshToken: any;
  expiresAt: any;
  athleteId: any;
  userName: any;
  totalDistance: number = 0;
  totalTime: number = 0;
  totalrun:number = 0;
  decimalKilometer: any;
  distanceYear: any;
  expired: boolean=false;
  monthId:any;
  topperData:TopperData[]=[];
  
  
  constructor(public dialog: MatDialog,
    private router: Router,
    private runnerProfileService:RunnerProfileService,
    private _snackBar: MatSnackBar,
    private stravaService: StravaService,
    private oauthService: OAuthService,){

  }

  ngOnInit(){
    this.getEventNames();
    this.getAllMarathonData();
    this.getTopperData();
  }

  updateData(){
    this.runnerProfileService.getAllAcesToken().subscribe( {
      next:(res)=>{
        console.log('Get Topper sucess');
        this.topperData=res;
        this.monthId='Whole-Year';
        this.topperdataSource= new MatTableDataSource(res);
        this.topperdataSource.paginator=this.topperMarathon;
        this.topperdataSource.sort=this.toppersort;
        console.log(this.topperData);
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  getEventNames(){
    this.runnerProfileService.getAllFutureMarathonData().subscribe( {
      next:(res)=>{
        this.eventNames=res;
        console.log(this.eventNames);
        console.log('Success');
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  getAllMarathonData(){
    this.runnerProfileService.getAllRunnerData().subscribe( {
      next:(res)=>{
        console.log('Get All sucess');
        this.dataSource= new MatTableDataSource(res);
        this.dataSource.paginator=this.paginator;
        this.dataSource.sort=this.sort;
        console.log(this.dataSource);
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  getTopperData(){
    this.runnerProfileService.getTopperData().subscribe( {
      next:(res)=>{
        console.log('Get Topper sucess');
        this.monthId='Whole-Year';
        this.topperData=res;
        this.topperdataSource= new MatTableDataSource(res);
        this.topperdataSource.paginator=this.topperMarathon;
        this.topperdataSource.sort=this.toppersort;
        console.log(this.topperData);
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  editMarathon(row: any){
    this.dialog.open(RunnerDetailComponent, {
      width:'30%',
      data:row
    }).afterClosed().subscribe(val=>{
      if(val ==='Saved'){
          this.getAllMarathonData();
      }
    });
  }

  downloadPdf(row: any){
    this.router.navigate(["generate-pdf"]);
    this.runnerProfileService.setProfileId(row['profileId']);
  }

  deleteMarathonData(row:any){
    this.runnerProfileService.deleteMarathonData(row.registerId).subscribe( {
      next:(res)=>{
        this.getAllMarathonData();
        console.log('Get All sucess');
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  addMarathonDetail(){
    if(this.marathonEvent.eventName && this.marathonEvent.eventYear ){
      this.runnerProfileService.addMarathonDetail(this.marathonEvent).subscribe( {
        next:(res)=>{
          console.log('Add Marathon Event Success');
          this.getEventNames();
          this._snackBar.open("Event Added Successfully!", "Undo", {
            duration: 1000
          });
        },
        error:()=>{
          console.log('failure');
        }
      });
    }else{
      this._snackBar.open("Please Enter some value!", "Undo", {
        duration: 1000
      });
    }
  }

  generateReport(){
    this.runnerProfileService.downloadPdf(this.selectedList.value);
  }

  logout(){
    this.router.navigate(["login"]);
  }
  
  selectedValue(event: MatSelectChange) {
    this.marathonId= event.value;
    this.eventName= event.source.triggerValue;
    var year = this.eventNames.filter(
      event => event.marathonId === this.marathonId);
    this.eventYear=year[0].eventYear;
 
}
  selectedMonth($event: any){
    this.monthId=$event.value;
    if(this.monthId==='Whole-Year'){
      this.topperdataSource= new MatTableDataSource(this.topperData);
      this.topperdataSource.paginator=this.topperMarathon;
      this.topperdataSource.sort=this.toppersort;
    }else{
      this.topperData[0].selectMonth=this.monthId;
      this.runnerProfileService.replaceByMonth(this.topperData).subscribe(
        response => {
            this.topperdataSource= new MatTableDataSource(response);
            this.topperdataSource.paginator=this.topperMarathon;
            this.topperdataSource.sort=this.toppersort;
      });
    }
  }

  downloadEvent(){
    this.runnerProfileService.downloadEventPdf(this.marathonId);
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
        this.athleteId=this.activities[0]['athlete']['id'];
      }
      console.log('Activities:', this.activities);
      console.log('Athlete Id:', this.athleteId);
     
        this.wholeyearDataEn.userName=this.userName;
        this.wholeyearDataEn.code=localStorage.getItem('access_token');
        this.wholeyearDataEn.refreshToken=this.refreshToken;
        this.wholeyearDataEn.expiresAt=this.expiresAt;
        this.wholeyearDataEn.athleteId=this.athleteId;
        this.wholeyearDataEn.totalKilometer=this.distanceYear;
        this.wholeyearDataEn.decimalKilometer=this.decimalKilometer;
        this.runnerProfileService.addWholeYearData(this.wholeyearDataEn).subscribe(
          response => {
            console.log('Success');
          });
      
    }
  );
}

openDialog() {
    this.dialog.open(RunnerDetailComponent, {
      width:'30%'
    }).afterClosed().subscribe(val=>{
      if(val ==='Saved'){
          this.getAllMarathonData();
      }
    });
  }

}
