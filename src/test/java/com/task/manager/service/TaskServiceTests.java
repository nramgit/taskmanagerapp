package com.task.manager.service;

import com.task.manager.domain.TaskRequestDto;
import com.task.manager.domain.TaskResponseDto;
import com.task.manager.domain.TaskStatus;
import com.task.manager.entity.TaskEntity;
import com.task.manager.exception.TaskNotFoundException;
import com.task.manager.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskServiceTests {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateTask() {
        TaskService taskService = new TaskService(taskRepository);
        TaskResponseDto taskResponseDto =  taskService.createTask(new TaskRequestDto(
                "TestTitle",
                new Date(),
                TaskStatus.NOT_STARTED));
        Assertions.assertNotNull(taskResponseDto);

        System.out.println("Test testCreateTask Successful!");
    }

    @Test
    public void testGetTask() {
        TaskService taskService = new TaskService(taskRepository);
        TaskResponseDto taskResponseDto =  taskService.getTaskById(new Long(2));
        //String exceptionMsg = Assertions.assertDoesNotThrow(() -> {return new TaskNotFoundException();});
        Assertions.assertEquals(Assertions.assertDoesNotThrow(() -> {return new TaskNotFoundException();}),"exceptionMsg");
        System.out.println("Test testGetTask Successful!");
    }
}
