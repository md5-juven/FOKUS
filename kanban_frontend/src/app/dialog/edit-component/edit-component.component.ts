import { Component, Inject, Input } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSelectChange } from '@angular/material/select';
import { task, user } from 'src/app/model/project';
import { DataStreamService } from 'src/app/services/data-stream.service';
import { ProjectService } from 'src/app/services/project.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-edit-component',
  templateUrl: './edit-component.component.html',
  styleUrls: ['./edit-component.component.css']
})
export class EditComponentComponent {


  constructor(public dialogRef: MatDialogRef<EditComponentComponent>, private project: ProjectService, private token: TokenStorageService, @Inject(MAT_DIALOG_DATA) public data: any, private stream: DataStreamService) { }

  memberCount:Map<string,number> = new Map()
  task: task = {}

  formData: task = {}

  emails: user[] = [];

  selectedValue?: string[] = [];
  limitedMember?: string[] = [];

  statuses: string[] = ['TO DO', 'In Progress', 'Submitted', 'Completed']

  creator: any;

  ngOnInit() {
    console.log(this.data);
    this.task = this.data
    this.stream.currebtMembers.subscribe((data) => {
      this.emails = data;
      console.log(this.emails);
    })
    this.stream.currentMemberCount.subscribe(data=>{
      this.memberCount = data
    })
    if (this.task.memberList != null) {
      for (let values of this.task.memberList!) {
        this.selectedValue?.push(values.email!);
      }
    }
    console.log(this.selectedValue);
    this.stream.currentCreator.subscribe( data =>{
      this.creator = data;
    })
    this.emailReduser()
  }

  updatedTask() {
    let tempMember: user[] = [];
    for (let temp of this.emails) {
      for (let exVlaue of this.selectedValue!) {
        if (temp.email === exVlaue) {
          tempMember.push(temp);
        }
      }
    }
    console.log(this.selectedValue);
    console.log(tempMember);
    this.task.memberList = [];
    this.task.memberList = tempMember;
    console.log(this.task);

    const project_id: number = this.token.getProjectId()
    this.task.lastChangedBy = window.localStorage.getItem("user-email")??'';
    this.project.updateTaskStatus(project_id, this.task).subscribe({
      next: data => {
        console.log(data)
      }
    })
    this.dialogRef.close();
  }

  changeStatus(task: task) {
    const project_id: number = this.token.getProjectId()
    this.project.updateTaskStatus(project_id, task).subscribe({
      next: data => {
        console.log(data)
      }
    })
  }
  cancelEdit() {
    this.dialogRef.close();
  }
  isOptionDisabled(opt: any): boolean {
    // console.log('isOptionDisabled')
    // console.log(opt)
    let data = this.selectedValue?.length! >= 3 && !this.selectedValue?.find(elm => elm == opt)
    // console.log(data)
    return data
  }
  emailReduser(){
    this.limitedMember = [];
    for(let temp of this.emails){
      if(this.memberCount.has(temp.email!)){
        if(this.memberCount.get(temp.email!) ==3)
        this.limitedMember?.push(temp.email!)
      }
    }
    console.log(this.limitedMember)
  }
   value:number=0
  isOprionLimiter(opt:string):boolean{
    // console.log(this.value++)
    // console.log(opt)
    let data = this.limitedMember?.find(elm => elm == opt)?this.selectedValue?.find(elm => elm == opt)?false:true:false
    // console.log('isOprionLimiter')
    // console.log(data)
    return data
  }
}
