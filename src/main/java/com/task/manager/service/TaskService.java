package com.task.manager.service;

import com.task.manager.domain.TaskRequestDto;
import com.task.manager.domain.TaskResponseDto;
import com.task.manager.entity.TaskEntity;
import com.task.manager.exception.TaskNotFoundException;
import com.task.manager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @Autowired
    ModelMapper mapper;

    public TaskResponseDto createTask(TaskRequestDto taskDto) {

        ModelMapper mapper = new ModelMapper();
        TaskEntity task = mapper.map(taskDto,TaskEntity.class);
        /*TaskEntity task = new TaskEntity();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(Date.from(Instant.now()));
        task.setStatus(taskDto.getStatus());
        */
        TaskEntity taskEntityUpdated = taskRepository.save(task);
        return mapper.map(taskEntityUpdated,TaskResponseDto.class);
        //return convertEntityToResponse(taskEntityUpdated);
    }

    public List<TaskResponseDto> getAllTasks() {
        List<TaskEntity> tasks = taskRepository.findAll();
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        TaskResponseDto taskResponseDto = null;
        for(TaskEntity task : tasks)
            taskResponseDtoList.add(convertEntityToResponse(task));

        return taskResponseDtoList;
    }
    private TaskResponseDto convertEntityToResponse(TaskEntity taskEntity) {
        return TaskResponseDto.builder()
                .title(taskEntity.getTitle())
                .dueDate(taskEntity.getDueDate())
                .status(taskEntity.getStatus())
                .build();
    }

    public TaskResponseDto getTaskById(Long id) {
        return taskRepository.findById(id).map(taskEntity -> {
            return mapper.map(taskEntity, TaskResponseDto.class);
        }).orElseThrow(() -> new TaskNotFoundException());

    }

    public String deleteTaskById(Long id) {
        taskRepository.deleteById(id);
        return "Task is deleted successfully";
    }

    public TaskResponseDto updateTaskById(Long id, TaskRequestDto taskRequestDto) {

        return taskRepository.findById(id).map(taskEntity -> {
            mapper.map(taskRequestDto,TaskEntity.class);
       /*   taskEntity.setTitle(title);
            taskEntity.setDueDate(newTask.getDueDate());
            taskEntity.setStatus(newTask.getStatus());*/
            //return convertEntityToResponse(taskRepository.save(taskEntity));
            return mapper.map(taskRepository.save(taskEntity), TaskResponseDto.class);
        }).orElseThrow(() -> new TaskNotFoundException());
    }

    public TaskResponseDto replaceTaskById(Long id, TaskRequestDto taskDto) {

        TaskEntity newTask = new TaskEntity();
        newTask.setTitle(taskDto.getTitle());
        newTask.setDueDate(taskDto.getDueDate());
        newTask.setStatus(taskDto.getStatus());

        return taskRepository.findById(id).map(taskEntity -> {
            taskEntity.setTitle(newTask.getTitle());
            taskEntity.setDueDate(newTask.getDueDate());
            taskEntity.setStatus(newTask.getStatus());
            return convertEntityToResponse(taskRepository.save(taskEntity));
        }).orElseGet(() -> {
            return convertEntityToResponse(taskRepository.save(newTask));
        });
    }
}
