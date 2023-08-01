import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from '../services/login.service';
import { RouterService } from '../services/router.service';
// import { ConfirmedValidator } from './confirmed.validator';

@Component({
  selector: 'app-reset-pass',
  templateUrl: './reset-pass.component.html',
  styleUrls: ['./reset-pass.component.css']
})
export class ResetPassComponent {

  constructor(private actRout: ActivatedRoute, private loginSrv:LoginService,private fb: FormBuilder,
              private navigate: RouterService){}

  token:string = "";
  validity?:boolean;
  validityForm?:boolean=true
  validitySuccess?:boolean;
  email:string ="";
  time: number = 3;
  interval:any;
  updated:string="";
  
  ngOnInit():void{
    this.actRout.queryParams.subscribe(data =>{
                this.token = data['key']
              })
    this.loginSrv.verifyToken(this.token).subscribe({
      next: data=>{
        this.email = data.email;
        this.validity = true;
        this.validityForm = true;
      },
      error : err=>{
        console.log("not-valid")
        this.validity = false
        this.validityForm = false;
      }
    })
  }
  formPass = this.fb.group({
    password: ['', [Validators.required]],
    confirm_password: ['', [Validators.required]]
  },{ 
    validator: ConfirmedValidator('password', 'confirm_password')
  }
  )
  get passwordMatchError() {
    return (
      this.formPass.getError('mismatch') &&
      this.formPass.get('confirm_password')?.touched
    );
  }

  onSubmit(){
    this.loginSrv.resetPass(this.email,this.formPass.value.password,this.token).subscribe({
      next: data =>{
        this.updated = data;
        this.validitySuccess = true;
        this.validityForm = false;
        this.interval = setInterval(() => {
          this.time--;
          if(this.time == 0){
            this.navigate.toLogin();
          }
        },1000)
      },
      error: err=>{
        console.log("error")
        console.log(err)
      }
    })
  }
}


  function ConfirmedValidator(source: string, target: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const sourceCtrl = control.get(source);
    const targetCtrl = control.get(target);

    return sourceCtrl && targetCtrl && sourceCtrl.value !== targetCtrl.value
      ? { mismatch: true }
      : null;
    }
  }

