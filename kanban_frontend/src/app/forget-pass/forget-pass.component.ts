import { Component } from '@angular/core';
import { LoginComponent } from '../login/login.component';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-forget-pass',
  templateUrl: './forget-pass.component.html',
  styleUrls: ['./forget-pass.component.css']
})
export class ForgetPassComponent {

  constructor(private Login:LoginService){}

  email:string = "";
  sent?:boolean;
  error?:boolean;
  
  submit(){
    this.Login.forgetPassword(this.email).subscribe({
      next : data =>{
        this.sent = true;
      },
      error : err =>{
        this.error = true;
        this.email = "";
      }
    })
    
  }
  changeError(){
    this.error = false;
  }
}
