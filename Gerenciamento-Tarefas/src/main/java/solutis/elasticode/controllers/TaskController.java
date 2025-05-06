package solutis.elasticode.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solutis.elasticode.dtos.TaskMapping;
import solutis.elasticode.dtos.TaskDto;
import solutis.elasticode.entities.Task;
import solutis.elasticode.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;
    private final TaskMapping mapping;

    public TaskController(TaskService service, TaskMapping mapping) {
        this.service = service;
        this.mapping = mapping;
    }

    @PostMapping
    public ResponseEntity<TaskDto> criartask(@RequestBody TaskDto dto) {
        return ResponseEntity.status(201).body(mapping.toDto(service.criarTask(dto)));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listarTasks() {
        List<Task> tasks = service.listarTasks();

        return ResponseEntity.status(200).body(tasks.stream().map(mapping::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> buscarTask(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(mapping.toDto(service.buscarTask(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> atualizarTask(@RequestBody TaskDto dto, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(mapping.toDto(service.atualizarTask(dto, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Integer id) {
        service.deletarTask(id);

        return ResponseEntity.status(204).build();
    }

}
