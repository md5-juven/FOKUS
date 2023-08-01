import { Overlay } from '@angular/cdk/overlay';
import { Component, Injectable, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { EditComponentComponent } from '../dialog/edit-component/edit-component.component';
import { task, user } from '../model/project';
import { ProjectDetailsComponent } from '../project-details/project-details.component';
import { DataStreamService } from '../services/data-stream.service';
import { LoginService } from '../services/login.service';
import { ProjectService } from '../services/project.service';
import { TokenStorageService } from '../services/token-storage.service';


@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrls: ['./task-card.component.css']
})
export class TaskCardComponent {

  constructor(private project: ProjectService, private token: TokenStorageService, private loginService: LoginService,
              private emails: DashboardComponent, private projectService: ProjectDetailsComponent,private stream:DataStreamService,
              private overlay:Overlay, private dialog: MatDialog) { }

  @Input()
  task: task = {}

  formData: task = {}

  firstLetter: any;

  userDetails: any;
  isOpen = false;
  isMemberOpen = false
  isArchive:any;
  isArchiveBoolean?:boolean

  visibility: boolean = true;

  statuses: string[] = ['TO DO', 'In Progress', 'Submitted', 'Completed']

  members: user[] = [];

  creator: any;

  ngOnInit(){
    this.stream.currebtMembers.subscribe(data=> this.members = data)
    console.log(this.members)
    this.stream.currentCreator.subscribe(data =>{
      this.creator = data;
    })
    this.stream.currentArchive.subscribe(data=> this.isArchive=data)
    console.log(this.isArchive)
    if(this.isArchive === 'ARCHIVE'){
      this.isArchiveBoolean = true
    }
  }

  taskDetails(task: any){
    console.log(task);
    const dialogRef = this.dialog.open(EditComponentComponent, {
      data: task
    });
  }

  changeVisibility() {
    this.visibility = !this.visibility;
  }


  changepriority(data: any) {
    const project_id: number = this.token.getProjectId()
    this.isOpen = !this.isOpen
    console.log(data);
    this.task.priority = data;
    console.log(this.task)
    console.log(project_id)
    this.project.updateTaskStatus(project_id, this.task).subscribe({
      next: data => {
        console.log(data)
      }
    })
  }
  changeStatus(task: task) {
    const project_id: number = this.token.getProjectId()

    this.project.updateTaskStatus(project_id, task).subscribe({
      next: data => {
        console.log(data)
      }
    })

  }

  deleteTask(task: any) {
    let userSelection = confirm('The Project will get deleted.\n It cannot be restored again')
    let project_id: number = this.token.getProjectId()
    if (userSelection) {
      this.project.deleteTask(project_id, task).subscribe(
        response => {
          console.log("deleted")
          // window.location.reload()
        })
    }
  }






}
