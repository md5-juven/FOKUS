import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, tap } from 'rxjs';
import { project, task, user } from '../model/project';
import { TokenStorageService } from './token-storage.service';


function _global_window(): any{
  return window;
}

const PROJECT_API = 'http://localhost:9500/kan/project/';
@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private refreshData = new Subject<void>()
  constructor(private http : HttpClient,private token : TokenStorageService) { }

  get RefreshRequired(){
    return this.refreshData
  }

  get nativeWindow(): any{
    return _global_window();
  }


  createProject(project : any): Observable<any>{
   return this.http.post(PROJECT_API+'newProject',project).pipe(
    tap(()=>{
      this.RefreshRequired.next()
    })
   );   
  }


  getProjects(): Observable<any>{
    return this.http.get(PROJECT_API+'getProjects')
  }

  addTask(project_id : number|undefined,task : task): Observable<any>{
    return this.http.post(PROJECT_API+'addTask/'+project_id,task).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }

  assignMember(project_id :number|undefined,email : string | undefined ): Observable<any>{
    let user : user = {}
    user.email = email
    return this.http.post(PROJECT_API+'assign/'+project_id,user).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }

  updateTaskStatus(project_id : number ,task : task): Observable<any>{
    return this.http.put(PROJECT_API+'updateTask/'+project_id,task).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }

  deleteProject(project_id : number) : Observable<any>{
    return this.http.delete(PROJECT_API+'deleteProject/'+project_id).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }

  deleteTask(project_id : number,task : task) : Observable<any>{
    const httpOptions = {headers : new HttpHeaders({'Content-Type': 'application/json'}),
     body:task}
    return this.http.delete<any>(PROJECT_API+'deleteTask/'+project_id,httpOptions).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }


  getAssignedProjects(): Observable<any>{
    return this.http.get(PROJECT_API+'getAssigned')
  }

  getEmailsStartWith(startWith:string):Observable<any>{
    return this.http.get("http://localhost:9500/kanban/requiredMail?StartWith="+startWith)
  }

  //remove member form the project
  removeMember(projectId:number,email:string){
      return this.http.delete(PROJECT_API+'removeMember/'+projectId+'?email='+email)
  }

  getProjectById(project_id : any): Observable<any>{
    let pId : any = this.token.getProjectId()
    return this.http.get(PROJECT_API+'getProject/'+project_id) 
  }

  archivedProjects(project_id: any, status: any){
    return this.http.post(PROJECT_API+'archive/'+project_id+'?archiveStatus='+status, null).pipe(
      tap(()=>{
        this.RefreshRequired.next()
      })
    )    
  }

  getAllTask(id: any){
    return this.http.get(PROJECT_API+'getAllTask/'+id)
  }

}
