package com.example.portal.scheduler;

import com.example.portal.TenantContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;


@Scope("prototype")
@Component
public class TaskDefinitionBean implements Runnable {

    private TaskDefinition taskDefinition;
    private final TransactionTemplate transactionTemplate;

    public TaskDefinitionBean(final TransactionTemplate transactionTemplate, TaskDefinition taskDefinition) {
        this.transactionTemplate = transactionTemplate;
        this.taskDefinition = taskDefinition;
    }

    @Override
    public void run() {
        try {
            TenantContext.setCurrentTenant(taskDefinition.getTenant());
            transactionTemplate.execute(transactionStatus -> {
                System.out.println("Tenant: " + taskDefinition.getTenant() + " Running with context: " + TenantContext.getCurrentTenant() + " thread: " + Thread.currentThread().getName());
//                System.out.println("Running action: " + taskDefinition.getActionType());
//                System.out.println("With Data: " + taskDefinition.getData());

                if (taskDefinition.getActionType().equals("SLOW")) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
                return true;
            });
        } finally {
            TenantContext.clear();
        }
    }
}
