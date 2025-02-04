import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RunnersAdminComponent } from 'src/app/components/runners-admin/runners-admin.component';
import { RunnersProfileComponent } from './components/runners-profile/runners-profile.component';
import { AppRoutingModule } from './app-routing.module';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule} from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MarathonDetailComponent } from './components/marathon-detail/marathon-detail.component';
import {MatDialogModule} from '@angular/material/dialog'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import {FormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import { RunnerDetailComponent } from './components/runner-detail/runner-detail.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { LoginComponent } from './components/login/login.component';
import {MatCardModule} from '@angular/material/card';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core'
import { NativeDateAdapter }from '@angular/material/core';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { StravaloginComponent } from './components/stravalogin/stravalogin.component';
import { CallbackComponent } from './components/callback/callback.component';
import { ActivitiesComponent } from './components/activities/activities.component';
import {MatListModule} from '@angular/material/list';
import { GeneratePdfComponent } from './components/generate-pdf/generate-pdf.component';

@NgModule({
  declarations: [
    AppComponent,
    RunnersAdminComponent,
    RunnersProfileComponent,
    MarathonDetailComponent,
    RunnerDetailComponent,
    LoginComponent,
    StravaloginComponent,
    CallbackComponent,
    ActivitiesComponent,
    GeneratePdfComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatExpansionModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatListModule
    
  ],
  providers: [NativeDateAdapter],
  bootstrap: [AppComponent]
})
export class AppModule { }
