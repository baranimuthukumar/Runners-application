import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDeatils } from '../marathon-profile';
import { RunnerProfileService } from 'src/app/services/runner-profile.service';
import { MatDialog } from '@angular/material/dialog';
import { RunnerDetailComponent } from '../runner-detail/runner-detail.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private router: Router,
    private runnerProfileService:RunnerProfileService,
    public dialog: MatDialog,) { }

  username:string = '';
  password:string = '';
  loginDeatils:LoginDeatils= new LoginDeatils();
  errorValue:string='Invalid Credentials';
  error:boolean=false;
  
    ngOnInit() {
    }
  
    login() : void {
      this.loginDeatils.userName=this.username;
      this.loginDeatils.password=this.password;
      this.runnerProfileService.checkLoginDetails(this.loginDeatils).subscribe( {
        next:(res)=>{
          if(res['valid'] ){
             if(res['userName']==='Admin') {
              this.router.navigate(["runners-admin"]);
             }else{
              this.router.navigate(["runners-profile"]);
              this.runnerProfileService.setProfileId(res['profileId']);
             }
             this.error=false;
          }else{
            this.error=true;
          }
        },
        error:()=>{
          console.log('failure');
        }
      });
    }

    register() {
      this.dialog.open(RunnerDetailComponent, {
        width:'30%'
      }).afterClosed().subscribe(val=>{
        if(val ==='Saved'){
          this.router.navigate(["runners-profile"]);
        }
      });
    }

}
