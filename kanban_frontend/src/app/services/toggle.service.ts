import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToggleService {

  constructor() { }

  toggle : boolean = false

  toggleStatus = new EventEmitter<boolean>

  navToggle(){
    this.toggleStatus.emit(!this.toggle)
  }
}
