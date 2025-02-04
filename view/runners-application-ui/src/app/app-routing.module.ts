import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { RunnersAdminComponent } from './components/runners-admin/runners-admin.component';
import { RunnersProfileComponent } from './components/runners-profile/runners-profile.component';
import { LoginComponent }from './components/login/login.component';
import { StravaloginComponent }from './components/stravalogin/stravalogin.component';
import { CallbackComponent }from './components/callback/callback.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import { GeneratePdfComponent } from './components/generate-pdf/generate-pdf.component'


const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'runners-admin', component: RunnersAdminComponent },
  { path: 'runners-profile', component: RunnersProfileComponent },
  { path: 'strava-login', component: StravaloginComponent },
  { path: 'callback', component: CallbackComponent },
  { path: 'activities', component: ActivitiesComponent },
  { path: 'generate-pdf', component: GeneratePdfComponent },
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
