package com.example.portal.scheduler.jobs;

import org.springframework.stereotype.Component;

@Component
public class DynamicTask {

    public void execute(String taskName, String taskParam) {
        System.out.println(taskName + " task is executed with param: " + taskParam);
    }
}
