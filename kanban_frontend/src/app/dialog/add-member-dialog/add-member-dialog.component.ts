import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { user } from 'src/app/model/project';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-add-member-dialog',
  templateUrl: './add-member-dialog.component.html',
  styleUrls: ['./add-member-dialog.component.css']
})
export class AddMemberDialogComponent {
  
  constructor(
    public dialogRef: MatDialogRef<AddMemberDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, 
    private project : ProjectService) {}

    filteredOptions:string[] =[];
    memberPre:string[] =[]

    ngOnInit(){
      this.data.email = null
      console.log(this.data.member)
      if(this.data.member!=null){
        for( let emailget of this.data.member){
          console.log(emailget.email)
          this.memberPre.push(emailget.email)
        }
        console.log(this.memberPre)
      }
      this.memberPre.push(this.data.adminMail)
    }

    
    getListOfEmails(){
      if(this.data.email.length >= 3){
        console.log(this.data.email)
        this.project.getEmailsStartWith(this.data.email).subscribe({
          next:list=>{
            if(list.length>0){
              let deleteList = new Set(this.memberPre)
              let filterMail:string[] = list
              this.filteredOptions = list
              this.filteredOptions = filterMail.filter((name)=>{
                return !deleteList.has(name)
              })
            }else{
              this.filteredOptions = ["no data"]
            }
          }
        })
      }else{
        this.filteredOptions=[]
      }
    }


    onNoClick(): void {
      console.log("close")
      this.dialogRef.close();
    }

}
