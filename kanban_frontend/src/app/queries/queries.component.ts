import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { messageData } from '../model/queries';
import { ChatService } from '../services/chat.service';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-queries',
  templateUrl: './queries.component.html',
  styleUrls: ['./queries.component.css']
})
export class QueriesComponent {
  constructor(private queriSend: ChatService, private router: RouterService, private snack: MatSnackBar) { }
  messagedata: messageData = {
    name: '',
    email: '',
    message: ''
  }

  sendquerie() {
    console.log(this.messagedata)
    if (this.messagedata.name != '' && this.messagedata.email != '' && this.messagedata.message != '') {
      this.queriSend.sendQuerie(this.messagedata).subscribe({
        next: data => {
      this.messagedata.name = ''
      this.messagedata.email = ''
      this.messagedata.message = ''
      this.messagedata.contact = undefined;
      this.snack.open("Your Message is Received, We will get back to you soon", "OK", {
        duration: 3000
      })
      this.router.toHome();   
        },
        error: err => {
        }
      })
    }
  }
}
