package com.task.manager.domain;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
    private String title;
    private Date dueDate;
    private TaskStatus status;
}
