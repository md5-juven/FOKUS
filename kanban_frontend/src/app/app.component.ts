import { Component, HostListener } from '@angular/core';
import { LoginService } from './services/login.service';
import { RouterService } from './services/router.service';
import { TokenStorageService } from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Fokus';

//   @HostListener('window:beforeunload', ['$event'])
//   beforeunloadHandler(e: Event) {
//   localStorage.clear();
// }
  isLoggedIn = false;
   constructor(private token : TokenStorageService, private route : RouterService,private loginService : LoginService){}

   loginStatus : boolean = false

   ngOnInit() : void{
    let user = this.token.getToken();
    if(user){
      console.log(user)
      this.isLoggedIn = true;
    }else 
    this.isLoggedIn =false

    this.loginService.loginStatus.subscribe( (staus)=>{
      this.isLoggedIn = staus
    })
  } 

  logout(): void{
    this.token.logOut()
    this.isLoggedIn = false

  }

  login():void{
    this.route.toLogin()
  }


}
