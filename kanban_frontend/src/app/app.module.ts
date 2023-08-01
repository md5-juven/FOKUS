import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule,HTTP_INTERCEPTORS  } from '@angular/common/http';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { InterceptorService } from './services/interceptor.service';
import { NotFoundComponent } from './not-found/not-found.component';
import {MatDialogModule} from '@angular/material/dialog';
import { ProjectDialogComponent } from './dialog/project-dialog/project-dialog.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { TaskDialogComponent } from './dialog/task-dialog/task-dialog.component';
import { FormsModule } from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import { AddMemberDialogComponent } from './dialog/add-member-dialog/add-member-dialog.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTooltipModule } from '@angular/material/tooltip';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCardModule,MatCardHeader} from '@angular/material/card';
import { TaskBoardComponent } from './task-board/task-board.component';
import { TaskCardComponent } from './task-card/task-card.component'
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { LayoutModule } from '@angular/cdk/layout';

import {ScrollingModule} from '@angular/cdk/scrolling';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import {GoogleLoginProvider, FacebookLoginProvider} from '@abacritt/angularx-social-login';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SupportComponent } from './support/support.component';
import { ForgetPassComponent } from './forget-pass/forget-pass.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';
import { FooterComponent } from './footer/footer.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { PricingsComponent } from './pricings/pricings.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { DatePipe } from '@angular/common';
import {MatBadgeModule} from '@angular/material/badge';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import {OverlayModule} from '@angular/cdk/overlay';
import { QueriesComponent } from './queries/queries.component';
import { EditComponentComponent } from './dialog/edit-component/edit-component.component';
import { DeleteVerifyDialogComponent } from './dialog/delete-verify-dialog/delete-verify-dialog.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    HomeComponent,
    NotFoundComponent,
    ProjectDialogComponent,
    ProjectDetailsComponent,
    TaskDialogComponent,
    AddMemberDialogComponent,
    TaskBoardComponent,
    TaskCardComponent,
    NavBarComponent,
    HomeComponent,
    ContactUsComponent,
    LoginComponent,
    RegisterComponent,
    SupportComponent,
    FooterComponent,
    PricingsComponent,
    AboutUsComponent,
    ForgetPassComponent,
    ResetPassComponent,
    EditProfileComponent,
    QueriesComponent,
    EditComponentComponent,
    DeleteVerifyDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
    MatDialogModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatRadioModule,
    MatSidenavModule,
    MatExpansionModule,
    MatTooltipModule,
    MatMenuModule,
    MatTabsModule,
    LayoutModule,
    ScrollingModule,
    MatToolbarModule,
    MatCardModule,
    SocialLoginModule,
    FlexLayoutModule,
    MatAutocompleteModule,
    MatProgressBarModule,
    DragDropModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    OverlayModule

  ],
  providers: [ DatePipe, {provide:HTTP_INTERCEPTORS,useClass: InterceptorService,multi:true},MatDatepickerModule, {
    provide: 'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [
        {
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(
            '1010487201024-5afoeor6v4440ogm6bttlcqsm918i8of.apps.googleusercontent.com'
          )
        },
        {
          id: FacebookLoginProvider.PROVIDER_ID,
          provider: new FacebookLoginProvider('clientId')
        },
      ],
      onError: (err) => {
        console.error(err);
      }
    } as SocialAuthServiceConfig,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
