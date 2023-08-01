import { Injectable } from '@angular/core';
import {BehaviorSubject, map} from 'rxjs';
import { project, task, user } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class DataStreamService {

  private SorcetimeLine = new BehaviorSubject<number>(0);
  currentTimeLine = this.SorcetimeLine.asObservable()
  private sorceProject = new BehaviorSubject<project>({});
  currentProject = this.sorceProject.asObservable()
  private sorceMembers = new BehaviorSubject<Array<user>>([])
  currebtMembers = this.sorceMembers.asObservable()
  private sorceCreator = new BehaviorSubject<boolean>(true);
  currentCreator = this.sorceCreator.asObservable()
  private sorceArchive = new BehaviorSubject<string>("LIVE")
  currentArchive = this.sorceArchive.asObservable()

  //member count in a taskList
  private sourceMemberCount = new BehaviorSubject<Map<string,number>>(new Map())
  currentMemberCount = this.sourceMemberCount.asObservable()

  constructor() { }

  changeDuration(duration:number){
    this.SorcetimeLine.next(duration)
  }

  changeCreator(value: boolean){
    this.sorceCreator.next(value)
  }

  changeProject(project:project){
    this.sorceProject.next(project)
  }

  changeTask(task: any){
    this.sorceProject.next(task);
  }
  changeMembers(members:user[]){
    this.sorceMembers.next(members)
  }

  changeArchive(value: string){
    this.sorceArchive.next(value)
  }
  changeMemberCounter(memberCount:Map<string,number>){
    this.sourceMemberCount.next(memberCount)
  }
}
