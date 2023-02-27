package com.example.portal.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class TaskDefinitionBeanFactory {

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TaskDefinitionBeanFactory(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public TaskDefinitionBean create(TaskDefinition taskDefinition) {
        return new TaskDefinitionBean(transactionTemplate, taskDefinition);
    }
}