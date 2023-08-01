import { SocialAuthService } from '@abacritt/angularx-social-login';
import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { LoginService } from '../services/login.service';
import { RouterService } from '../services/router.service';
import { TokenStorageService } from '../services/token-storage.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

 
  hide = true;

  result : any;
  errorMessage : any 
  role = null;
  user: any;
  constructor(private fb: FormBuilder,private loginService : LoginService,
    private token : TokenStorageService,
    private route : RouterService, private snackBar : MatSnackBar, private socialServie: SocialAuthService){}
  
    @ViewChild(RegisterComponent) swiper?: RegisterComponent;

    ngOnInit():void {
      console.log(this.loginService.isPurchaseClick)
        this.socialServie.authState.subscribe( (user) =>{
          this.user = user;
          console.log(this.user);
        })
    }

    selectChange(event: any){
      if(event.target.files){
        let reader= new FileReader();
        reader.readAsDataURL(event.target.files[0]);
        reader.onload = (e: any) =>{
          this.registerForm.value.profile_pic = e.target.result;
        }
       }
    }

  registerForm = this.fb.group({
    email : ['',[Validators.required,Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")]],
    password : ['',[Validators.required,Validators.minLength(8)]],
    name : ['',[Validators.required,Validators.minLength(3)]],
    profile_pic : ['']
  })

  get email(){return this.registerForm.get("email");}
  get name(){return this.registerForm.get("name");}
  get password(){return this.registerForm.get("password");}

  onRegister() : any{
    console.log(this.registerForm.value.email)
    console.log(this.registerForm.value.name)
    console.log(this.registerForm.value.password)
     let name : any;
     let email : any
     let password : any
     let profile_pic: any

     
     if(this.registerForm.value.name){
       name = this.registerForm.value.name;
       email = this.registerForm.value.email
       password = this.registerForm.value.password
       profile_pic = this.registerForm.value.profile_pic
     }
     else{
      name = this.user.name;
      email = this.user.email;
      profile_pic = this.user.photo;
      password = null;
      console.log("Else con")

     }
    //  let email : any;
    //  let password : any = this.registerForm.value.password
     this.loginService.register(name,email,password,profile_pic).subscribe({
      next : data =>{   
        console.log(data)
  
        this.loginService.login(email,password).subscribe({
          next: data =>{
            this.token.saveToken(data.token);
            this.token.saveUser(data);
            this.token.saveEmail(data.email);
            window.localStorage.setItem("username", data.username);
            window.localStorage.setItem("title", data.title);
            this.route.toDashboard();
          }
        })
        

        this.snackBar.open('Registration Successful', 'OK', {
          duration: 3000
        });
      
      },
      error: err => {
        this.errorMessage = err.error.message;
        console.log(this.errorMessage)
        alert("Registration Failed, Please try after sometime")
      }
     })
  }
  onChange(event:any){
    if(event.target.value.length>4){
      console.log(event.target.value)
    }
  }
}
