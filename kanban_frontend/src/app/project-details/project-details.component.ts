import { JsonpInterceptor } from '@angular/common/http';

import { Component, Inject, Injectable, Input } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddMemberDialogComponent } from '../dialog/add-member-dialog/add-member-dialog.component';
import { project, task, user } from '../model/project';
import { ProjectService } from '../services/project.service';
import { TokenStorageService } from '../services/token-storage.service';
import { TaskDialogComponent } from '../dialog/task-dialog/task-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DatePipe } from '@angular/common';
import { LoginService } from '../services/login.service';
import { DataStreamService } from '../services/data-stream.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { DeleteVerifyDialogComponent } from '../dialog/delete-verify-dialog/delete-verify-dialog.component';



@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent {

  @Input()
  projectDetails: project = {}

  constructor(public dialog: MatDialog, private project: ProjectService, private token: TokenStorageService,
              private snackBar: MatSnackBar, private loginServ: LoginService, private stream:DataStreamService) { }

  // task: task[] = []
  title: boolean = false;
  userName: string | any = '';
  firstLetter: any;

  creator: any;

  timeLine:number=0;

  isNotNull: boolean = false;

  emails: any;

  hidden: boolean = false;

  isArchiveStatus: any;


  //task complition %
  @Input()
  value: any;

  ngOnInit() {
    this.stream.currentTimeLine.subscribe(data => this.timeLine =data)
    // this.stream.currentProject.subscribe()
    if (window.localStorage.getItem("title") === "PREMIUM") {
      this.title = true;
    }
    this.userName = window.localStorage.getItem("username");
    console.log(this.userName);
    this.userName = this.userName.toUpperCase();
    this.emails = window.localStorage.getItem('user-email');
    this.stream.currentCreator.subscribe( data =>{
      this.creator = data;
    })

    this.stream.currentArchive.subscribe( data =>{
      this.isArchiveStatus = data;
    })
    this.emails = window.localStorage.getItem('email');
  }

  taskDialog(): void {
    if (this.projectDetails.project_id ?? 0 != 0) {
      const dialogRef = this.dialog.open(TaskDialogComponent, {
        data: { project_id: this.projectDetails?.project_id, emailList: this.projectDetails.assigned_empl },
      });
     
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        // this.projectDetails = result;
        console.log("tl :: " + this.projectDetails)
        console.log(result); 
      });
    } else {
      this.snackBar.open('Please select project before adding task !!', '', {
        duration: 2000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
    }

  }

  email: string = ''
  addMember(): void {
    console.log(this.projectDetails)
    if (this.projectDetails.project_id ?? 0 != 0) {
      const dialogRef = this.dialog.open(AddMemberDialogComponent, {
        data: { project_id: this.projectDetails?.project_id, email: this.email, member: this.projectDetails?.assigned_empl, adminMail: this.projectDetails.email },
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        this.email = result;
        console.log("tl :: " + this.email)
        if (this.email != null && this.email.length > 10) {
          this.project.assignMember(this.projectDetails.project_id, this.email).subscribe({
            next: data => {
              console.log(data)
            }, error: err => {
              this.snackBar.open('Failed to Invite Member. Please enter only registered member email !!', 'Ok', {
                duration: 3000,
                horizontalPosition: 'right',
                verticalPosition: 'top',
              });
            }
          })
        }
      });
    } else {
      this.snackBar.open('Please select project before adding members !!', '', {
        duration: 2000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
    }
  }

  deleteProject() {
    let project_id: number = this.projectDetails.project_id ?? 0;
    console.log(project_id)
    if (project_id == 0) {
      this.snackBar.open('Please select project before deleting !!', '', {
        duration: 2000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
      });
    } else {
      let statusArc: string;

      if (this.projectDetails.archive === 'LIVE') {
        statusArc = 'ARCHIVE';
        let userSelection = confirm('Project ID    :  ' + this.projectDetails.project_id + '\nProject Title :  ' + this.projectDetails.name +
        '\nThe project will be ARCHIVED')
      if (userSelection) {
        this.project.archivedProjects(project_id, statusArc).subscribe(
          response => {
            console.log(response);
            console.log(statusArc)
            // window.location.reload()
          })
      }
      } else {
          const archiveRef = this.dialog.open(DeleteVerifyDialogComponent,
                              {data: this.projectDetails});
          archiveRef.afterClosed().subscribe(data=>{
            console.log(data)
            // window.location.reload()
          })
          
      }
      
    }
  }

  // remove member from project
  removeMember(data: user) {
    this.project.removeMember(this.projectDetails.project_id ?? 0, data.email ?? "").subscribe({
      next: reply => {
        console.log(reply)
      }
    })
    console.log(data.email)
  }


  changeProjectState(){
    this.project.archivedProjects(this.projectDetails.project_id,"LIVE").subscribe({
      next:data=>{
        console.log(data)
      }
    })
  }

}
