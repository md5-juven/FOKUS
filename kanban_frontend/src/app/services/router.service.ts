import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private router : Router) { }

  toDashboard(){
    this.router.navigate(['dashboard'])
  }

  toLogin(){
    this.router.navigate(['login'])
  }
  
  toRegister(){
    this.router.navigate(['register'])
  }

  toHome(){
    this.router.navigate(['home'])
  }

  toContact(){
    this.router.navigate(['contact'])
  }
  toPricing(){
    this.router.navigate(['pricing'])
  }
}
