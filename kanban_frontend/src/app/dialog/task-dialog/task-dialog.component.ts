import { Component, Inject } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProjectDetailsComponent } from 'src/app/project-details/project-details.component';
import { DataStreamService } from 'src/app/services/data-stream.service';
import { task, user } from '../../model/project';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-task-dialog',
  templateUrl: './task-dialog.component.html',
  styleUrls: ['./task-dialog.component.css']
})
export class TaskDialogComponent {

  formData: task = {
    status: 'TO DO'
  }
  selectedValue?: string[] = [];
  emails: user[] = [];
  projectDetail: any = {}
  memberCount:Map<string,number> = new Map()

  user: user = {};

  constructor(
    public dialogRef: MatDialogRef<TaskDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private project: ProjectService, private stream: DataStreamService, private projects: ProjectDetailsComponent) { }

  priorities: string[] = ['Low', 'Moderate', 'High']
  statuses: string[] = ['TO DO', 'In Progress']

  ngOnInit() {
    // will log the entire data object
    console.log(this.data.project_id)
    console.log(this.data.emailList)
    this.stream.currentMemberCount.subscribe(data=>{
      this.memberCount = data
    })
    this.emails = this.data.emailList;
    if(this.emails!=null){
      this.emailReduser()
    }
  }

  addTask(task: NgForm) {
    let tempMember: user[] = [];
    if(this.emails!= null){
      for (let temp of this.emails) {
        for (let exVlaue of this.selectedValue!) {
          if (temp.email === exVlaue) {
            tempMember.push(temp);
          }
        }
      }
    }
    task.value.memberList = [];
    task.value.memberList = tempMember;
    task.value.lastChangedBy = window.localStorage.getItem('user-email');
    if (task.value) { 
      this.project.addTask(this.data.project_id, task.value).subscribe({
        next: data => {
          console.log(data)
        }
      })
    }
  }

  onNoClick(): void {
    console.log("Hiii");
    this.dialogRef.close();
    this.getAllTask();
  }
  isOptionDisabled(opt: any): boolean {
    return this.selectedValue?.length! >= 3 && !this.selectedValue?.find(elm => elm == opt)
  }


  // getUpdatedProjectDetails() {
  //   this.project.getProjectById().subscribe({
  //     next: data => {
  //       console.log(data)
  //       this.projectDetail = data
  //     }
  //   })
  // }

  getAllTask(){
    console.log("inside");
    this.project.getAllTask(this.data.project_id).subscribe({
      next: data =>{
        console.log(data);
      }
    })
  }

  emailReduser(){
    for(let temp of this.emails){
      if(this.memberCount.has(temp.email!)){
        if(this.memberCount.get(temp.email!) ==3)
        this.emails = this.emails.filter(remove=>remove.email!= temp.email)
      }
    }
  }

}
