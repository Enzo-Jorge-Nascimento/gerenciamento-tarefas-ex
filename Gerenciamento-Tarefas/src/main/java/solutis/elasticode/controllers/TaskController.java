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

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskDto> criartask(@RequestBody TaskDto requestDto) {
        return ResponseEntity.status(201).body(TaskMapping.toDto(service.criarTask(requestDto)));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listarTasks() {
        List<Task> tasks = service.listarTasks();

        return ResponseEntity.status(200).body(tasks.stream().map(TaskMapping::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> buscarTask(@PathVariable Long id) {
        return ResponseEntity.status(200).body(TaskMapping.toDto(service.buscarTask(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> atualizarTask(@RequestBody TaskDto requestDto, @PathVariable Long id) {
        return ResponseEntity.status(200).body(TaskMapping.toDto(service.atualizarTask(requestDto, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id) {
        service.deletarTask(id);

        return ResponseEntity.status(204).build();
    }

}
