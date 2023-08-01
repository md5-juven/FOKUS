import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-delete-verify-dialog',
  templateUrl: './delete-verify-dialog.component.html',
  styleUrls: ['./delete-verify-dialog.component.css']
})
export class DeleteVerifyDialogComponent {

  inputText?: string;

  projectTitle?: string;

  inputEqual?: boolean;

  constructor(public dialogRef: MatDialogRef<DeleteVerifyDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    private project: ProjectService) { }

  ngOnInit() {
    console.log(this.data)
    this.projectTitle = this.data.name
  }
  changeValue() {
    if (this.inputText === this.projectTitle) {
      this.inputEqual = true;
    } else
      this.inputEqual = false
  }

  deleteProject() {
    this.project.deleteProject(this.data.project_id).subscribe({
      next: data => {
        console.log(data)
        // window.location.reload()
        this.dialogRef.close();

      }
    })
  }
  cancle() {
    this.dialogRef.close();
  }
}
