import { Component, Host, HostListener, Injectable } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { TokenStorageService } from '../services/token-storage.service';
import { LoginService } from '../services/login.service';
import { RouterService } from '../services/router.service';
import { ToggleService } from '../services/toggle.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { user } from '../model/project';

@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private token : TokenStorageService, private route : RouterService, private loginService : LoginService, private toggle :ToggleService, private snackBar: MatSnackBar) {}

  isLoggedIn = false;

  isOpen = false;

  isAdmin = false;

  loginStatus : boolean = false
  

  userDetails: user = {};

  firstLetter: any;

  tokenExist: any;

  ngOnInit() : void{
   let user = this.token.getToken();
   this.tokenExist = user;
   if(user){
     console.log(user)
     this.isLoggedIn = true;

     this.loginService.getUserDetails().subscribe({
      next: data =>{
        this.userDetails = data;
        this.firstLetter = this.userDetails?.username?.charAt(0);
      },
      error: err =>{
        console.log(err);
        this.isLoggedIn =false;
      }
    })


   }else 
   this.isLoggedIn =false

   this.loginService.loginStatus.subscribe( (staus)=>{
     this.isLoggedIn = staus
   })
   this.loginService.adminStatus.subscribe( (staus)=>{
    this.isAdmin = staus
  })  


  //window.addEventListener('scroll',this.changeBg)
 } 

 toolbar_variable : boolean = false
 
 @HostListener('window:scroll',['$event']) scrollFunc(e : Event){
 
    let scrollValue = (e.target as Element).scrollTop 
    
  if (scrollValue > 20)
  {
    this.toolbar_variable = true
  }
  else
  {
    this.toolbar_variable = false
  }
    
}
 

 logout(): void{
   this.token.logOut()
   this.isLoggedIn = false;
   this.userDetails = {};
   this.isOpen = false;
   this.tokenExist="";
   this.snackBar.open('Log Out in Successfully', 'OK', {
    duration: 3000
  });
   this.route.toHome();

 }

 login():void{
   this.route.toLogin()
 }

 
sideNavToggle(){
 this.toggle.navToggle()
}


}
