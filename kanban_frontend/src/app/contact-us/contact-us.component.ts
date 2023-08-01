import { Component } from '@angular/core';

import { NgForm } from '@angular/forms';
import { message } from '../model/chat';
import { ChatService } from '../services/chat.service';
import { LoginService } from '../services/login.service';
import { RouterService } from '../services/router.service';
import { TokenStorageService } from '../services/token-storage.service';
import { interval, observable, Observable, Subscription } from 'rxjs';
import { switchMap, takeWhile } from 'rxjs/operators';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent {

  constructor(private chat : ChatService, private token : TokenStorageService,private login : LoginService,
    private route : RouterService){}

  formData : any ={}

  messages$ : message[] = []

  email : any =''

  isLoggedIn : boolean = false;

  chatUpdate : any 

  title: boolean = false;

  // async ngOnInit() { 
  //   while (true) { 
  //   await this.getMessage() 
  //   this.chat.newChat().subscribe({
  //         next: data=>{
  //           console.log(data)
  //         }
  //       })
    
  //       //this.getMessage()
  //       this.email = this.token.getEmail()
    
  //       this.login.loginStatus.subscribe((status)=>{
  //         this.isLoggedIn = status
  //         console.log(this.isLoggedIn)
  //       })
    
  //     await this.delay(1000); 
  //   } 
  // } 
  // delay(milliseconds: number) { 
  //   return new Promise(resolve => setTimeout(resolve, milliseconds)); 
  // }


  ngOnInit(){

    this.chat.newChat().subscribe({
      next: data=>{
        console.log(data)
      }
    })

    //this.getMessage()
    this.email = this.token.getEmail()

    this.login.loginStatus.subscribe((status)=>{
      this.isLoggedIn = status
      console.log(this.isLoggedIn)
    })

    this.chatUpdate = setInterval(() => {
      this.getMessage()
      console.log("interval")
       
     }, 1000);


     if (window.localStorage.getItem("title") === "FREE") {
      this.title = true;
    }
    
  }

  sendMessage(chat : NgForm){
    this.chat.newMessage(chat.value).subscribe({
      next: data=>{
        chat.reset()
       
      }
    })
    //this.route.toContact()
  }

  getMessage(){
    this.chat.getMessages().subscribe({
      next : data =>{
        this.messages$ = data
      }
    })
  }

  ngOnDestroy() {
    if (this.chatUpdate) {
      clearInterval(this.chatUpdate);
    }
  }


}
