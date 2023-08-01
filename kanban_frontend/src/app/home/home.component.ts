import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  
  name: any;

  ngOnInit(): void{
    this.name = window.localStorage.getItem('username');
  }
}
