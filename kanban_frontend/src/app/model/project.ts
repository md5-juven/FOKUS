export type project = {
    "project_id"? : number;
    "name"? : string;
    "description"? : string;
    "duration"? : Date;
    "startDate"?: Date;
    "taskList"? : task[];
    "email"? : string;
    "archive"?: string; 
    "assigned_empl"?: user[]
}

export type task = {
    "name"? : string;
    "description"? : string;
    "status"? : string;
    "priority"? : string;
    "memberList"?:user[];
    "lastChangedBy"?: string;
}

export type user = {
    "email"? : string;
    "username"?: string;
    "title"?: string;
    "profile_pic"?: string;
    "taskCount"?: number;
    "password"?:string
}