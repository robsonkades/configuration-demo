package com.example.portal.scheduler;

import com.example.portal.scheduler.jobs.TaskRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/scheduler")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskRegister taskRegister;

//    @Autowired
//    private TaskDefinitionBeanFactory taskDefinitionBeanFactory;


//    @PostMapping(path = "/tasks", consumes = "application/json", produces = "application/json")
//    public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
//        TaskDefinitionBean taskDefinitionBean = taskDefinitionBeanFactory.create(taskDefinition);
//        taskSchedulingService.scheduleATask(UUID.randomUUID().toString(), taskDefinitionBean, taskDefinition.getCronExpression());
//    }


    @PostMapping(path = "/tasks", consumes = "application/json", produces = "application/json")
    public void scheduleATaskNew(@RequestBody TaskDefinition taskDefinition) {
        taskRegister.execute(taskDefinition);
    }

    @GetMapping(path = "/tasks/{jobId}")
    public void removeJob(@PathVariable String jobId) {
        taskSchedulingService.removeScheduledTask(jobId);
    }
}
