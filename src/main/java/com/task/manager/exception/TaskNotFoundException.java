package com.task.manager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(){
        super("Task Not Found!");
    }
}
