import { Component, OnInit,Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl} from '@angular/forms';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { MarathonEvent, MarathonProfile } from '../marathon-profile';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog'
import { MatSelectChange } from '@angular/material/select';

@Component({
  selector: 'app-marathon-detail',
  templateUrl: './marathon-detail.component.html',
  styleUrls: ['./marathon-detail.component.scss']
})
export class MarathonDetailComponent implements OnInit {

  constructor(private formBuilder:FormBuilder,
    private runnerProfileService:RunnerProfileService,
    @Inject(MAT_DIALOG_DATA) public editData:any,
    private matDialogRef:MatDialogRef<MarathonDetailComponent>,
    ){

  }
  marathonForm !: FormGroup;
  marathonProfile :MarathonProfile = new MarathonProfile();
  actionButton: string='Save';
  eventNames:MarathonEvent[]=[];
  marathonId:any;
  eventName:any;

  ngOnInit(): void{
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
    this.marathonForm = this.formBuilder.group({
      registerId:[''],
      marathonId:[''],
      eventName:['',Validators.required],
      year:['',Validators.required],
      profileId:[''],
      bestTime:[''],
      podium:[''],
      futureOrpast:['',Validators.required],
      distance:['',Validators.required],
      paymentReference:['',Validators.required],
    });
    if(this.editData){
      this.marathonId=this.editData.marathonId;
      this.eventName=this.editData.eventName;
      this.marathonForm.controls['registerId'].setValue(this.editData.registerId);
      this.marathonForm.controls['marathonId'].setValue(this.editData.marathonId);
      this.marathonForm.controls['eventName'].setValue(this.editData.marathonId);
      this.marathonForm.controls['year'].setValue(this.editData.year);
      this.marathonForm.controls['profileId'].setValue(this.editData.profileId);
      this.marathonForm.controls['bestTime'].setValue(this.editData.bestTime);
      this.marathonForm.controls['futureOrpast'].setValue(this.editData.futureOrpast);
      this.marathonForm.controls['distance'].setValue(this.editData.distance);
      this.marathonForm.controls['podium'].setValue(this.editData.podium);
      this.marathonForm.controls['paymentReference'].setValue(this.editData.paymentReference);
      this.actionButton='Update';
    }
  }
  addMarathonDetail(){
    if(this.marathonForm.valid){
      if(this.actionButton ==='Update'){
        this.marathonProfile.registerId=this.marathonForm.get("registerId")?.value;
      }
      this.marathonProfile.eventName=this.eventName;
      this.marathonProfile.year=this.marathonForm.get("year")?.value;
      this.marathonProfile.bestTime=this.marathonForm.get("bestTime")?.value;
      this.marathonProfile.futureOrpast=this.marathonForm.get("futureOrpast")?.value;
      this.marathonProfile.distance=this.marathonForm.get("distance")?.value;
      this.marathonProfile.paymentReference=this.marathonForm.get("paymentReference")?.value;
      this.marathonProfile.podium=this.marathonForm.get("podium")?.value;
      this.marathonProfile.marathonId=this.marathonId;
      this.marathonProfile.profileId=this.runnerProfileService.getProfileId();
      this.runnerProfileService.addUserProfile(this.marathonProfile).subscribe( {
        next:(res)=>{
          this.marathonForm.reset();
          this.matDialogRef.close('Saved');
        },
        error:()=>{
          console.log('failure');
        }
      });

    }
  }
  selectedValue(event: any) {
    console.log(event);
      this.marathonId= event.value;
      var year=this.eventNames.filter((person) => person.marathonId == this.marathonId);
      this.marathonForm.controls['year'].setValue(year[0].eventYear);
      this.eventName= event.source.triggerValue;
   
  }

}
