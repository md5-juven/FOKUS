import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterService } from 'src/app/services/router.service';
import { ProjectService } from '../../services/project.service';



@Component({
  selector: 'app-project-dialog',
  templateUrl: './project-dialog.component.html',
  styleUrls: ['./project-dialog.component.css'],
})

export class ProjectDialogComponent {

  constructor(private fb : FormBuilder, private project : ProjectService, private router : RouterService,
    private snackBar : MatSnackBar){}

  
  projectForm = this.fb.group({
    name : ['',[Validators.required,Validators.minLength(5)]],
    description : ['',[Validators.required]],
    startDate: new FormControl<Date | null>(null),
    duration: new FormControl<Date | null>(null),
  })

  get name(){return this.projectForm.get("name");}
  get description(){return this.projectForm.get("description");}
  get startDate(){return this.projectForm.get('startDate')}
  get duration(){return this.projectForm.get('duration')}

  time(){
 
    console.log(this.projectForm.value)
    this.project.createProject(this.projectForm.value).subscribe({
      next : data=>{
        console.log(data)
      },error: err=>{
        this.snackBar.open('Failed to Create Project', 'Ok', {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
        });
      }
    })
    this.router.toDashboard()
}
}
