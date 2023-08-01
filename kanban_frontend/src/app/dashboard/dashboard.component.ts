import { Component, Injectable, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { project, user } from '../model/project';
import { ProjectDialogComponent } from '../dialog/project-dialog/project-dialog.component';
import { ProjectService } from '../services/project.service';
import { ToggleService } from '../services/toggle.service';
import { TokenStorageService } from '../services/token-storage.service';
import { RouterService } from '../services/router.service';

import { MatSnackBar } from '@angular/material/snack-bar';

import { DatePipe } from '@angular/common';
import { LoginService } from '../services/login.service';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { DataStreamService } from '../services/data-stream.service';
import { ProjectDetailsComponent } from '../project-details/project-details.component';


@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor(private dialog: MatDialog, private project: ProjectService,
    private toggle: ToggleService, private token: TokenStorageService, private router: RouterService,
    private snackBar: MatSnackBar, private serv: LoginService, private navbar: NavBarComponent,
    private stream:DataStreamService, private projectDetail: ProjectDetailsComponent) { }

  projectDuration?:number;
  
  projectsList$: project[] = []

  //task complition %
  value: any;

  assignedProjects: project[] = [];

  archiveProjects: project[] = []

  tempProject: project[] = []

  selectedProject: project = {}

  showFiller = false;

  totalTask?: number = 0

  completedTask: number = 0;

  temp: any;

  sidenav: boolean = false

  badgeValue: any;

  assignedValue: any;

  archiveValue: number | any;

  archiveData: boolean = false;

  valueData: boolean = false;

  assignValue: boolean = false;

  isActive: boolean = false;

  userDetails: user = {}

  timeLine: number=0;

  email: any;



  ngOnInit() {
    this.stream.currentProject.subscribe(data =>{
    let update = this.projectsList$.findIndex(onj => onj.name == data.name);
      console.log(this.projectsList$[update])
      this.projectsList$[update] =data;
    })
    this.assignValue = false;
    this.valueData = false;
    this.navbar.ngOnInit();
    this.getCreatedProjects()
    this.project.RefreshRequired.subscribe(respose => {
      this.getCreatedProjects()
      if(this.token.getProjectId()){
        let pId : any = this.token.getProjectId()
      
        this.project.getProjectById(pId).subscribe({
          next: data =>{
            this.selectedProject = data
          }
        })
      }
    })

    this.getAssignedProjects()
    this.project.RefreshRequired.subscribe(respose => {
      this.getAssignedProjects()
    })

    this.email = window.localStorage.getItem('user-email');
    
  }

  reload() {
    this.router.toDashboard();
  }

  createNewProject() {
    if (this.checktitleCondition()) {
      const dialogRef = this.dialog.open(ProjectDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
      });
    } else {
      this.snackBar.open('Free user limiter to 3 project Only', 'Ok', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    }
  }
  calculateDate(project: project) {
    {
      const start = new Date(project.startDate!)
      const end = new Date(project.duration!).getDate()
      console.log(start.getDate() + "    " + end)
      this.stream.changeDuration(start.getDate())
    }
  }

  showCreatedProjects(project: project) {
    this.getCreatedProjects()
    // let tempU  = this.projectsList$.filter(obj=> obj.project_id = project.project_id)
    // console.log(this.projectsList$)
    // console.log(project)
    // console.log(tempU)
    this.showProjectDetails(project)
    this.calculateDate(project)
    if(project.assigned_empl!= null){
      this.stream.changeMembers(project.assigned_empl)
    }
    this.isActive = true;
    if(this.email === project.email){
      console.log("hello");
      // this.creator = false;
      this.stream.changeCreator(false);
    }
    this.stream.changeArchive(project.archive!);
  }

  showAssignedProjects(project: project) {
    console.log('assigned project')
    console.log(project)
    this.getAssignedProjects()
    this.stream.changeCreator(true);
    if(project.assigned_empl!= null){
      this.stream.changeMembers(project.assigned_empl)
    }
    this.stream.changeArchive(project.archive!);
    this.showProjectDetails(project)
    
  }

  showArchiveProjects(project: project){
    if(project.assigned_empl!= null){
      this.stream.changeMembers(project.assigned_empl)
    }
    this.getAssignedProjects()
    this.showProjectDetails(project)
    this.stream.changeArchive(project.archive!);
  }

  showProjectDetails(project: project) {
    console.log(this.completedTask);
    this.completedTask = 0;
    this.selectedProject = project;
    console.log(typeof project.duration);
    this.token.saveProjectId(project?.project_id)
    this.totalTask = this.selectedProject.taskList?.length ?? 0;

    if (this.totalTask != 0) {
      for (let task of this.selectedProject.taskList!) {
        console.log(task.status);
        if (task.status === "Completed") {
          console.log("Inside Log");
          this.completedTask = this.completedTask + 1;
        }
      }
      console.log(this.completedTask);
      this.temp = 100 / this.totalTask!;
      this.value = this.completedTask! * this.temp;
      this.value = this.value.toFixed(0);
    }

  }


  getCreatedProjects() {
    this.projectsList$ = [];
    this.archiveProjects = [];
    this.project.getProjects().subscribe({
      next: data => {
        console.log(data)
        this.tempProject = data;
        if (this.tempProject.length > 0) {
          for (let temp of this.tempProject) {
            if (temp.archive === 'LIVE') {
              this.projectsList$.push(temp)
            } else {
              this.archiveProjects.push(temp)
            }
          }
        }
        // this.projectsList$ = data
        this.badgeValue = this.projectsList$.length;
        this.archiveValue = this.archiveProjects.length;
        console.log(this.projectsList$.length);

      }
    })
  }

  getAssignedProjects() {
    this.project.getAssignedProjects().subscribe({
      next: data => {
        console.log(data)
        this.assignedProjects = data
        this.assignedValue = this.assignedProjects.length;
      }
    })
  }


  checktitleCondition() {
    let title = window.localStorage.getItem("title")
    let projectLength = this.projectsList$.length
    if (title === "FREE" && projectLength < 3)
      return true
    if (title === "FREE" && projectLength >= 3)
      return false
    if (title != "FREE")
      return true
    return false

  }

  matBadgeTogglor(title: string) {
    if (title === 'PROJECT') {
      this.valueData = !this.valueData;
    }
    if (title === 'ASSIGNED') {
      this.assignValue = !this.assignValue;
    }
    if (title === 'ARCHIVE') {
      this.archiveData = !this.archiveData;
    }
  }
  
}
