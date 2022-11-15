package com.task.manager.controller;


import com.task.manager.domain.TaskRequestDto;
import com.task.manager.domain.TaskResponseDto;
import com.task.manager.entity.TaskEntity;
import com.task.manager.exception.TaskNotFoundException;
import com.task.manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/task")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto){
        TaskResponseDto taskResponseDto = taskService.createTask(taskRequestDto);
        return new ResponseEntity<>(taskResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<TaskResponseDto>> getTasks() {
        List<TaskResponseDto> taskResponseDtoList = taskService.getAllTasks();
        return new ResponseEntity<List<TaskResponseDto>>(taskResponseDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/tasks/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.getTaskById(id);
        if(Objects.isNull(taskResponseDto))
            return new ResponseEntity<TaskResponseDto>(taskResponseDto, HttpStatus.NO_CONTENT);
        return new ResponseEntity<TaskResponseDto>(taskResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String res = taskService.deleteTaskById(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PatchMapping (value = "/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestBody TaskRequestDto taskRequestDto,@PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.updateTaskById(id,taskRequestDto);
        return new ResponseEntity<TaskResponseDto>(taskResponseDto, HttpStatus.OK);
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<TaskResponseDto> replaceTask(@RequestBody TaskRequestDto taskRequestDto, @PathVariable Long id) {
        TaskResponseDto taskResponseDto =  taskService.replaceTaskById(id,taskRequestDto);
        return new ResponseEntity<TaskResponseDto>(taskResponseDto, HttpStatus.OK);
    }

    @ExceptionHandler({
            TaskNotFoundException.class
    })
    ResponseEntity<String> handleException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
