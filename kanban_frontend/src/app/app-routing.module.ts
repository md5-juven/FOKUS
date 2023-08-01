

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ForgetPassComponent } from './forget-pass/forget-pass.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { PricingsComponent } from './pricings/pricings.component';
import { QueriesComponent } from './queries/queries.component';
import { RegisterComponent } from './register/register.component';
import { ResetPassComponent } from './reset-pass/reset-pass.component';
import { AuthGuard } from './services/auth.guard';
import { SupportComponent } from './support/support.component';

const routes: Routes = [
  {path:"",component:HomeComponent },
  {path : "login",component: LoginComponent},
  {path: "register", component: RegisterComponent},
  {path: "dashboard", component: DashboardComponent, canActivate: [AuthGuard]},
  {path: "query", component: QueriesComponent},
  {path: "home",component: HomeComponent},
  {path: "chat", component: ContactUsComponent, canActivate: [AuthGuard]},
  {path: "pricing", component: PricingsComponent},
  {path: "about", component: AboutUsComponent},
  {path: "edit", component: EditProfileComponent, canActivate: [AuthGuard]},
  {path: "support",component: SupportComponent, canActivate: [AuthGuard]},
  {path: "forgetPassword",component:ForgetPassComponent, canActivate: [AuthGuard]},
  {path: "reset_password/forget",pathMatch : 'full',component:ResetPassComponent, canActivate: [AuthGuard]},
  {path : "**",component: NotFoundComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
