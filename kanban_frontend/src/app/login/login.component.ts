import { Component, EventEmitter } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../services/login.service';
import { RouterService } from '../services/router.service';
import { TokenStorageService } from '../services/token-storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  name :any = '';

  hide = true;
  constructor(private fb: FormBuilder, private loginService : LoginService, 
    private token : TokenStorageService,
    private route : RouterService,private snackBar: MatSnackBar){}
  
  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
    }
    
  }


  loginForm = this.fb.group({
    email : ['',[Validators.required,Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")]],
    password : ['',[Validators.required]]
  })

  get email(){return this.loginForm.get("email");}
  get password(){return this.loginForm.get("password");}

  onLogin(){
    console.log(this.loginForm.value)
    let email : any = this.loginForm.value.email
    let password : any = this.loginForm.value.password
    this.loginService.login(email,password).subscribe({
      next : data =>{
        this.token.saveToken(data.token);
        this.token.saveUser(data);
        this.token.saveEmail(data.email);
        window.localStorage.setItem("username", data.username);
        window.localStorage.setItem("title", data.title);

        // this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.name = this.token.getUser().username;
       
        this.route.toDashboard()
        this.snackBar.open('Logged in Successfully', 'OK', {
          duration: 3000
        });
            
      },error : err=>{
        console.error(err.message)
        alert("Login Failed, Please try after sometime")
      }
    })
   
  
  }
}
