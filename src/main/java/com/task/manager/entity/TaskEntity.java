package com.task.manager.entity;

import com.task.manager.domain.TaskStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="due_dt")
    private Date dueDate;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @OneToMany(mappedBy = "task" , cascade = CascadeType.ALL)
    private List<NotesEntity> notes;

}
