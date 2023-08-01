import { Component, NgZone } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../services/login.service';
import { ProjectService } from '../services/project.service';
import { RouterService } from '../services/router.service';
import { TokenStorageService } from '../services/token-storage.service';

const STATUS = "PREMIUM"
@Component({
  selector: 'app-pricings',
  templateUrl: './pricings.component.html',
  styleUrls: ['./pricings.component.css']
})
export class PricingsComponent {


  name: any = "";
  email: any = '';
  rzp: any;
  title: any;
  redirect?: boolean;

  constructor(private projectService: ProjectService, private zone: NgZone, private loginServ: LoginService, private router: RouterService,private token:TokenStorageService, private snack: MatSnackBar) { }

  ngOnInit(): void {
    this.name = window.localStorage.getItem('username');
    this.email = window.localStorage.getItem('user-email');
    this.title = window.localStorage.getItem('title');
    console.log(this.email ?? "NO_USER");
  }

  freeMessage(){
    this.snack.open("Enjoy the Free Benefits No Need To Pay", 'OK', {
      duration: 3000
    })
  }


  razorPayOptions = {
    "key": "rzp_test_T3F42T2BUG0c08",
    "name": "FOKUS",
    "amount": "19999",
    "currency": "INR",
    "description": "PREMIUM PRODUCT",
    "image": "",
    "order_id": "",
    "prefill": {
      "name": this.name,
      "email": this.email,
      "contact": ""
    },
    "theme": {
      "color": "#f54803"
    },
    "handler": this.paymentHandler.bind(this),
    "modal": {
      ondismiss: (() => {
        window.location.href = "http://localhost:9000/pricing";
      })
    }
  };

  pay() {
    this.rzp = new this.projectService.nativeWindow.Razorpay(this.razorPayOptions);
    this.rzp.open();
  }

  paymentHandler(result: any) {
    console.log(result)
    this.updateTitle()
  }
  updateTitle() {
    console.log("inpaymentHandler")
    this.loginServ.updateTitle(STATUS, this.email).subscribe({
      next: data => {
        console.log(data.title)
        console.log(data.token)
        this.token.saveToken(data.token);
        this.token.saveUser(data);
        this.token.saveEmail(data.email);
        window.localStorage.setItem("username", data.username);
        window.localStorage.setItem("title", data.title);
        window.location.href = "http://localhost:9000/dashboard";

      }
    })
  }
  toSignUp() {
    this.loginServ.isPurchaseClick = true;
    this.router.toRegister()
  }
}
