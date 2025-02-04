import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RunnerProfile } from '../marathon-profile';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NativeDateAdapter }from '@angular/material/core';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-runner-detail',
  templateUrl: './runner-detail.component.html',
  styleUrls: ['./runner-detail.component.scss']
})
export class RunnerDetailComponent {

  constructor(private formBuilder:FormBuilder,
    private runnerProfileService:RunnerProfileService,
    @Inject(MAT_DIALOG_DATA) public editData:any,
    private matDialogRef:MatDialogRef<RunnerDetailComponent>,
    private dateAdapter: NativeDateAdapter,
    private uploadService: FileUploadService){
  }
  
  runnerForm !: FormGroup;
  runnerProfile :RunnerProfile = new RunnerProfile();
  actionButton: string='Save';
  selectedFiles?: FileList;
  currentFile?: File;
  message = '';

  fileInfos?: any;

  ngOnInit(): void{
    this.runnerForm = this.formBuilder.group({
      profileId:[''],
      email:['',Validators.required],
      userName:['',Validators.required],
      age:['',Validators.required],
      dob:['',Validators.required],
      contactNumber:['',Validators.required],
     // clubName:['',Validators.required],
      emergencyContactName:['',Validators.required],
      emergencyContactNumber:['',Validators.required],
      profession:['',Validators.required],
      ageCategory:['',Validators.required],
      personalBest:['',Validators.required],
      bloodGroup:['',Validators.required],
      tshirtSize:['',Validators.required],
      tshirtIssued:['',Validators.required],
      stravaLink:['',Validators.required],
      interestsOtherThanRunning:[''],
      tellAboutyourself:[''],
      
    });
    if(this.editData){
      this.runnerProfileService.setProfileId(this.editData.profileId);
      this.runnerForm.controls['age'].setValue(this.editData.age);
      this.runnerForm.controls['ageCategory'].setValue(this.editData.ageCategory);
    //  this.runnerForm.controls['clubName'].setValue(this.editData.clubName);
      this.runnerForm.controls['contactNumber'].setValue(this.editData.contactNumber);
      this.runnerForm.controls['dob'].setValue(this.dateAdapter.parse(this.editData.dob));
      this.runnerForm.controls['personalBest'].setValue(this.editData.personalBest);
      this.runnerForm.controls['bloodGroup'].setValue(this.editData.bloodGroup);
      this.runnerForm.controls['profileId'].setValue(this.editData.profileId);
      this.runnerForm.controls['email'].setValue(this.editData.email);
      this.runnerForm.controls['tshirtIssued'].setValue(this.editData.tshirtIssued);
      this.runnerForm.controls['tshirtSize'].setValue(this.editData.tshirtSize);
      this.runnerForm.controls['userName'].setValue(this.editData.userName);
      this.runnerForm.controls['emergencyContactName'].setValue(this.editData.emergencyContactName);
      this.runnerForm.controls['emergencyContactNumber'].setValue(this.editData.emergencyContactNumber);
      this.runnerForm.controls['profession'].setValue(this.editData.profession);
      this.runnerForm.controls['stravaLink'].setValue(this.editData.stravaLink);
      this.runnerForm.controls['interestsOtherThanRunning'].setValue(this.editData.interestsOtherThanRunning);
      this.runnerForm.controls['tellAboutyourself'].setValue(this.editData.tellAboutyourself);
      this.actionButton='Update';
      this.uploadService.getFiles(this.editData.profileId).subscribe({
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

  getFiles(profileId:any){
    this.uploadService.getFiles(profileId).subscribe({
      next: (event: any) => {
        if (event instanceof HttpResponse) {
          console.log('Barani');
          console.log(event.body);
          this.fileInfos=event.body;
          this.message = event.body.message;
          if(this.fileInfos.length > 0){
            this.runnerForm.reset();
            this.matDialogRef.close('Saved');
          }
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

  addRunnerDetail(){
    
    this.runnerProfile.age=this.runnerForm.get("age")?.value;
    this.runnerProfile.ageCategory=this.runnerForm.get("ageCategory")?.value;
  //  this.runnerProfile.clubName=this.runnerForm.get("clubName")?.value;
    this.runnerProfile.contactNumber=this.runnerForm.get("contactNumber")?.value;
    let date=this.dateAdapter.format(this.runnerForm.get("dob")?.value,'MM/dd/yyyy');
    this.runnerProfile.dob=date;
    this.runnerProfile.personalBest=this.runnerForm.get("personalBest")?.value;
    this.runnerProfile.bloodGroup=this.runnerForm.get("bloodGroup")?.value;
    this.runnerProfile.profileId=this.runnerForm.get("profileId")?.value;
    this.runnerProfile.email=this.runnerForm.get("email")?.value;
    this.runnerProfile.tshirtIssued=this.runnerForm.get("tshirtIssued")?.value;
    this.runnerProfile.tshirtSize=this.runnerForm.get("tshirtSize")?.value;
    this.runnerProfile.userName=this.runnerForm.get("userName")?.value;
    this.runnerProfile.emergencyContactName=this.runnerForm.get("emergencyContactName")?.value;
    this.runnerProfile.emergencyContactNumber=this.runnerForm.get("emergencyContactNumber")?.value;
    this.runnerProfile.profession=this.runnerForm.get("profession")?.value;
    this.runnerProfile.stravaLink=this.runnerForm.get("stravaLink")?.value;
    this.runnerProfile.interestsOtherThanRunning=this.runnerForm.get("interestsOtherThanRunning")?.value;
    this.runnerProfile.tellAboutyourself=this.runnerForm.get("tellAboutyourself")?.value;
    this.runnerProfileService.addRunnerProfile(this.runnerProfile).subscribe( {
      next:(res)=>{
        this.message="Please upload files"
        this.runnerProfileService.setProfileId(res['profileId']);
        this.runnerForm.controls['profileId'].setValue(res['profileId']);
        this.getFiles(res['profileId']);
      },
      error:()=>{
        console.log('failure');
      }
    });

}

OnDateChange(value:any){
  console.log('Barani-7');
  let date=this.dateAdapter.format(value,'MM/dd/yyyy');
  let dateVAl=this.dateAdapter.parse(date);
  console.log(date);
  console.log(dateVAl);
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
  this.uploadService.deletefile(this.editData.profileId,file.fileType).subscribe({
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
