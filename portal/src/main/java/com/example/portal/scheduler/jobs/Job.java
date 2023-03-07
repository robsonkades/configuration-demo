package com.example.portal.scheduler.jobs;


import com.example.portal.scheduler.TaskDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Job implements Task<TaskDefinition> {

    @Override
    public void execute(TaskDefinition taskDefinition) {
        ServiceContext.get().runWithTenant(taskDefinition.getTenant(), () -> {
            System.out.println("OverrideTenant: " + ServiceContext.get().getCurrentTenant());
            System.out.println("Data: " + taskDefinition.getData());
        });
    }
}
