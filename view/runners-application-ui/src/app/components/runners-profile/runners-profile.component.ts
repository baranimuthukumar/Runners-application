import { AfterViewInit,Component,ViewChild,OnInit} from '@angular/core';
import {
  MatDialog,
  MAT_DIALOG_DATA,
  MatDialogTitle,
  MatDialogContent,
} from '@angular/material/dialog';
import { MarathonDetailComponent } from '../marathon-detail/marathon-detail.component';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { RunnerDetailComponent } from 'src/app/components/runner-detail/runner-detail.component';
import { RunnerProfile } from '../marathon-profile';
import { Router } from '@angular/router';
import { NativeDateAdapter } from '@angular/material/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';

@Component({
  selector: 'app-runners-profile',
  templateUrl: './runners-profile.component.html',
  styleUrls: ['./runners-profile.component.scss']
})
export class RunnersProfileComponent {

  displayedColumns: string[] = ['serialNo', 'eventName', 'year', 'bestTime','futureOrpast','distance','action'];
  runnersdisplayedColumns: string[] =['serialNo','userName','age','ageCategory','personalBest'];
  dataSource!: MatTableDataSource<any>;
  runnerProfile: RunnerProfile = new RunnerProfile;
  runnersDataSource!: MatTableDataSource<any>;

  @ViewChild('paginatorOverAll') paginatorOverAll!: MatPaginator;
  @ViewChild('paginatorMarathon') paginatorMarathon!: MatPaginator;
  @ViewChild('runerTable', { read: MatSort, static: true }) sort!: MatSort;
  @ViewChild('overAllTable', { read: MatSort, static: true }) sortAll!: MatSort;

  step = 0;
  dobdate:any;
  selectedFiles?: FileList;
  currentFile?: File;
  message = '';

  fileInfos?: any;
  constructor(public dialog: MatDialog,
    private router: Router,
    private runnerProfileService:RunnerProfileService,
    private dateAdapter: NativeDateAdapter,
    private uploadService: FileUploadService) {}

  

  openDialog() {
    this.dialog.open(MarathonDetailComponent, {
      width:'30%'
    }).afterClosed().subscribe(val=>{
      if(val ==='Saved'){
        if(this.runnerProfileService.getProfileId()>0){
          this.getAllMarathonData(this.runnerProfileService.getProfileId());
        }
      }
    });
  }

  ngOnInit(){
    if(this.runnerProfileService.getProfileId()>0){
      this.getAllMarathonData(this.runnerProfileService.getProfileId());
    }
    this.getAllRunnersData();
    this.uploadService.getFiles(this.runnerProfileService.getProfileId()).subscribe({
      next: (event: any) => {
        if (event instanceof HttpResponse) {
          console.log('Barani');
          console.log(event.body);
          this.fileInfos=event.body;
          this.message = event.body.message;
        }
      },
      error: (err: any) => {
        console.log(err);

        if (err.error && err.error.message) {
          this.message = err.error.message;
        } else {
          this.message = 'Could not get the file!';
        }

        this.currentFile = undefined;
      }
    });
    
    //this.fileInfos = this.uploadService.getFiles();
    
  }

  getAllMarathonData(profileId:String){
    this.runnerProfileService.getAllMarathonData(profileId).subscribe( {
      next:(res)=>{
        console.log('Get All sucess');
        this.runnerProfile=res;
        this.dobdate=this.dateAdapter.parse(res['dob']);
        this.dataSource= new MatTableDataSource(res['marathonProfileList']);
        this.dataSource.paginator = this.paginatorMarathon;
        this.dataSource.sort = this.sort;
        console.log(this.dataSource);
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  getAllRunnersData(){
    this.runnerProfileService.getAllRunnerData().subscribe( {
      next:(res)=>{
        console.log('Get All sucess');
        this.runnersDataSource= new MatTableDataSource(res);
        this.runnersDataSource.paginator = this.paginatorOverAll;
        this.runnersDataSource.sort = this.sortAll;
        console.log(this.runnersDataSource);
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
    this.dialog.open(MarathonDetailComponent, {
      width:'30%',
      data:row
    }).afterClosed().subscribe(val=>{
      if(val ==='Saved'){
        if(this.runnerProfileService.getProfileId()>0){
          this.getAllMarathonData(this.runnerProfileService.getProfileId());
        }
      }
    });
  }

  deleteMarathonData(row:any){
    this.runnerProfileService.deleteMarathonData(row.registerId).subscribe( {
      next:(res)=>{
        if(this.runnerProfileService.getProfileId()>0){
          this.getAllMarathonData(this.runnerProfileService.getProfileId());
        }
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  updateRunnerDetail(){
    let date=this.dateAdapter.format(this.dobdate,'MM/dd/yyyy');
    this.runnerProfile.dob=date;
    this.runnerProfileService.addRunnerProfile(this.runnerProfile).subscribe( {
      next:(res)=>{
        this.runnerProfile=res;
      },
      error:()=>{
        console.log('failure');
      }
    });

  }

  logout(){
    this.router.navigate(["login"]);
  }

  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
  }

  upload(): void {

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);

      if (file) {
        this.currentFile = file;

        this.uploadService.upload(this.currentFile,this.runnerProfileService.getProfileId()).subscribe({
          next: (event: any) => {
            if(event instanceof HttpResponse) {
              console.log('Barani');
              console.log(event.body);
              this.fileInfos=event.body;
              this.message = event.body.message;
            }
          },
          error: (err: any) => {
            console.log(err);

            if (err.error && err.error.message) {
              this.message = err.error.message;
            } else {
              this.message = 'Could not upload the file!';
            }

            this.currentFile = undefined;
          }
        });
      }

      this.selectedFiles = undefined;
    }
  }

  deletefile(file :any){
    this.uploadService.deletefile(this.runnerProfileService.getProfileId(),file.fileType).subscribe({
      next: (event: any) => {
        if (event instanceof HttpResponse) {
          console.log('Barani');
          console.log(event.body);
          this.fileInfos=event.body;
          this.message = event.body.message;
        }
      },
      error: (err: any) => {
        console.log(err);

        if (err.error && err.error.message) {
          this.message = err.error.message;
        } else {
          this.message = 'Could not get the file!';
        }

        this.currentFile = undefined;
      }
    });

  }


}
