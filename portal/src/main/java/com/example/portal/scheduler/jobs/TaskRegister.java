package com.example.portal.scheduler.jobs;

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

    public void execute(String task, String cronExpression) {
        taskScheduler.schedule(() -> job.execute(task), new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
    }
}
