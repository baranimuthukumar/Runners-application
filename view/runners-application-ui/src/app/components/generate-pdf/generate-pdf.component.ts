import { Component } from '@angular/core';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { RunnerProfile } from '../marathon-profile';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-generate-pdf',
  templateUrl: './generate-pdf.component.html',
  styleUrls: ['./generate-pdf.component.scss']
})
export class GeneratePdfComponent {

  runnerProfile: RunnerProfile = new RunnerProfile;
  fileInfos?: any;
  message = '';

  constructor(
    private runnerProfileService:RunnerProfileService,
    private uploadService: FileUploadService) {}

  ngOnInit(){
    if(this.runnerProfileService.getProfileId()>0){
      this.getAllMarathonData(this.runnerProfileService.getProfileId());
    }
    this.uploadService.getFiles(this.runnerProfileService.getProfileId()).subscribe({
      next: (event: any) => {
        if (event instanceof HttpResponse) {
          console.log('Barani');
          console.log(event.body);
          this.fileInfos=event.body;
          this.message = event.body.message;
          console.log(this.fileInfos);
        }
      },
      error: (err: any) => {
        console.log(err);

        if (err.error && err.error.message) {
          this.message = err.error.message;
        } else {
          this.message = 'Could not get the file!';
        }
      }
    });
  }

  getAllMarathonData(profileId:String){
    this.runnerProfileService.getAllMarathonData(profileId).subscribe( {
      next:(res)=>{
        console.log('Get All sucess');
        this.runnerProfile=res;
        console.log(res);
      },
      error:()=>{
        console.log('failure');
      }
    });
  }

  generatePDF() {
    const data = document.getElementById('contentToConvert')!;
    html2canvas(data, {
      logging: true,
      allowTaint: true,
      useCORS: true,
      }).then(canvas => {
      const imgWidth = 210;
      const pageHeight = 295;
      const imgHeight = canvas.height * imgWidth / canvas.width;
      const heightLeft = imgHeight;

      const contentDataURL = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4'); // A4 size page of PDF

      let position = 0;
      pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, imgHeight);
      pdf.save(this.runnerProfile.userName+'_Data.pdf'); // Generated PDF
    });
  }

}
