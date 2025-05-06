package solutis.elasticode.dtos;

import org.springframework.stereotype.Component;
import solutis.elasticode.entities.Task;

@Component
public class TaskMapping {

    public Task toEntity(TaskDto entity) {
        if (entity == null) return null;

        return new Task(entity.getTitulo(), entity.getDescription(), entity.getStatus());
    }

    public TaskDto toDto(Task entity) {
        if (entity == null) return null;

        return new TaskDto(entity.getTitulo(), entity.getDescription(), entity.getStatus());
    }

}
