package com.example.portal.scheduler;

import com.example.portal.scheduler.jobs.TaskRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/scheduler")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskRegister taskRegister;

    @Autowired
    private TaskDefinitionBeanFactory taskDefinitionBeanFactory;


    @PostMapping(path = "/tasks", consumes = "application/json", produces = "application/json")
    public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        TaskDefinitionBean taskDefinitionBean = taskDefinitionBeanFactory.create(taskDefinition);
        taskSchedulingService.scheduleATask(UUID.randomUUID().toString(), taskDefinitionBean, taskDefinition.getCronExpression());
    }


    @PostMapping(path = "/tasks/new", consumes = "application/json", produces = "application/json")
    public void scheduleATaskNew(@RequestBody TaskDefinition taskDefinition) {
        TaskDefinitionBean taskDefinitionBean = taskDefinitionBeanFactory.create(taskDefinition);
        taskRegister.execute(taskDefinition.getTenant(), taskDefinition.getCronExpression());
    }

    @GetMapping(path = "/tasks/{jobId}")
    public void removeJob(@PathVariable String jobId) {
        taskSchedulingService.removeScheduledTask(jobId);
    }
}
