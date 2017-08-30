package com.appetite.appetiteoperationsapp;

import java.io.Serializable;

/**
 * Created by iammike on 23/03/16.
 */
public class TaskInfo implements Serializable {

    String id;
    String taskid;
    String clientid;
    String name;
    String client_deadline;
    String description;
    String status;
    String employee;
    String starttime;
    String endtime;
    String deadline;

    public TaskInfo(String id, String taskid, String clientid, String name , String client_deadline , String description ,  String status,
                     String employee, String starttime, String endtime, String deadline)
    {
        super();
        this.id=id;
        this.taskid=taskid;
        this.clientid=clientid;
        this.name=name;
        this.client_deadline=client_deadline;
        this.description=description;
        this.description=description;
        this.status=status;
        this.employee=employee;
        this.starttime=starttime;
        this.endtime=endtime;
        this.deadline=deadline;
    }
}
