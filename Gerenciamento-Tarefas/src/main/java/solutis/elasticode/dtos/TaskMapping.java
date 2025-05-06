package solutis.elasticode.dtos;

import solutis.elasticode.entities.Task;

public class TaskMapping {

    public static Task toEntity(TaskDto entity) {
        if (entity == null) return null;

        return new Task(entity.getTitulo(), entity.getDescription(), entity.getStatus());
    }

    public static TaskDto toDto(Task entity) {
        if (entity == null) return null;

        return new TaskDto(entity.getTitulo(), entity.getDescription(), entity.getStatus());
    }

}
