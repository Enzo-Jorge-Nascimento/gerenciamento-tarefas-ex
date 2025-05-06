package solutis.elasticode.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import solutis.elasticode.dtos.TaskMapping;
import solutis.elasticode.dtos.TaskDto;
import solutis.elasticode.entities.Task;
import solutis.elasticode.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task criarTask(TaskDto dto) {
        return repository.save(TaskMapping.toEntity(dto));
    }

    public List<Task> listarTasks() {
        return repository.findAll();
    }

    public Task buscarTask(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "A task de id %d não foi encontrada.".formatted(id)
                )
        );
    }

    public Task atualizarTask(TaskDto dto, Long id) {
        Task taskAtt = TaskMapping.toEntity(dto);

        taskAtt.setId(id);

        return repository.save(taskAtt);
    }

    public void deletarTask(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException(
                "A task de id %d não foi encontrada.".formatted(id)
        );

        repository.deleteById(id);
    }
}
