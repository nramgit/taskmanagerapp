package com.task.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskRequestDto {
    private String title;
    private Date dueDate;
    private TaskStatus status;
}
