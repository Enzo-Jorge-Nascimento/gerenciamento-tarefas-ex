package solutis.elasticode.dtos;

import solutis.elasticode.enums.Status;

public class TaskDto {

    private String titulo;
    private String description;
    private Status status;

    public TaskDto(String titulo, String description, Status status) {
        this.titulo = titulo;
        this.description = description;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
