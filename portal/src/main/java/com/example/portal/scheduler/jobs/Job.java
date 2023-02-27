package com.example.portal.scheduler.jobs;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Job implements Task<String> {

    @Override
    public void execute(String tenant) {
        System.out.println("Before - CurrentTenant: " +  ServiceContext.get().getCurrentTenant());


        ServiceContext.get().runWithTenant(tenant, () -> {
            System.out.println("OverrideTenant: " +  ServiceContext.get().getCurrentTenant());
        });
        System.out.println("After - CurrentTenant: " +  ServiceContext.get().getCurrentTenant());
    }
}
