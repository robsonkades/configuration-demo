package com.example.portal.scheduler.jobs;

import com.example.portal.scheduler.TaskDefinition;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class TaskRegister {

    private TaskScheduler taskScheduler;
    private Job job;

    public TaskRegister(TaskScheduler taskScheduler, Job job) {
        this.taskScheduler = taskScheduler;
        this.job = job;
    }

    public void execute(TaskDefinition taskDefinition) {
        taskScheduler.schedule(() -> job.execute(taskDefinition), new CronTrigger(taskDefinition.getCronExpression(), TimeZone.getTimeZone(TimeZone.getDefault().getID())));
    }
}
