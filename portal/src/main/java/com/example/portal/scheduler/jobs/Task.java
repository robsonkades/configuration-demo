package com.example.portal.scheduler.jobs;

@FunctionalInterface
public interface Task<T> {
    void execute(T t);
}
